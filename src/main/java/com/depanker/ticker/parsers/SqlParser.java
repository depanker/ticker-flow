package com.depanker.ticker.parsers;

import com.depanker.ticker.beans.OrderByColumns;
import com.depanker.ticker.beans.SelectColumns;
import com.depanker.ticker.beans.WhereCondition;
import com.depanker.ticker.beans.WhereConditions;
import com.depanker.ticker.services.ColumnValueTypeConverter;
import com.depanker.ticker.types.ColumnType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Data
@RequiredArgsConstructor
public class SqlParser {
    private final ColumnValueTypeConverter columnValueTypeConverterImpl;

    public List<OrderByColumns> getOrderByElements(PlainSelect ps) {
        List<OrderByElement> orderByElements = ps.getOrderByElements();
        if (orderByElements == null) {
            orderByElements = new ArrayList<>();
        }
        return orderByElements.stream()
                .map(orderByElement ->
                        new OrderByColumns(orderByElement.getExpression().toString(), !orderByElement.isAsc()))
                .collect(Collectors.toList());
    }

    public SelectColumns getSelectItems(Select select) throws JSQLParserException {
        List<SelectItem> selectItems = ((PlainSelect) select.getSelectBody()).getSelectItems();
        PlainSelect ps = (PlainSelect) select.getSelectBody();
        return new SelectColumns(selectItems.stream()
                .map(selectItem ->  selectItem.toString())
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .filter(s -> !"*".equalsIgnoreCase(s))
                .map(s -> s.toUpperCase())
                .collect(Collectors.toList()));
    }

    public String getFileName(Select select) {
        TablesNamesFinder tablesNamesFinder =  new TablesNamesFinder();
        return tablesNamesFinder.getTableList(select).stream()
                .findFirst().orElse("");
    }

    public WhereConditions getComparisonExpression(Expression expr) {
        List<WhereCondition> whereConditions  = new ArrayList<>();
        if (expr == null) {
            return new WhereConditions(whereConditions);
        }
        expr.accept(new ExpressionVisitorAdapter() {
                        @SneakyThrows
                        @Override
                        protected void visitBinaryExpression(BinaryExpression expr) {
                            if (expr instanceof ComparisonOperator) {
                                WhereCondition whereCondition =  new WhereCondition();
                                String colName =  expr.getLeftExpression().toString().strip().toUpperCase();
                                String op = expr.getStringExpression().strip();
                                String valueData =  expr.getRightExpression().toString().strip();
                                Object value;
                                if (valueData == null || "null".equalsIgnoreCase(valueData) || valueData.isBlank()) {
                                    value=null;
                                } else {
                                    value =  getColumnValue(colName, valueData);
                                }
                                whereCondition.setColumn(ColumnType.valueOf(colName));
                                whereCondition.setOperator(op);
                                whereCondition.setValue(value);
                                whereConditions.add(whereCondition);
                            }
                            super.visitBinaryExpression(expr);
                        }

                    }
        );

        return  new WhereConditions(whereConditions);
    }

    private Object getColumnValue(String colName, String valueData) throws Exception {
        return columnValueTypeConverterImpl.convert(colName, valueData);
    }

}
