package com.depanker.ticker.services.impl;

import com.depanker.ticker.beans.OrderByColumns;
import com.depanker.ticker.beans.SelectColumns;
import com.depanker.ticker.beans.TickerCase;
import com.depanker.ticker.beans.WhereConditions;
import com.depanker.ticker.parsers.SqlParser;
import com.depanker.ticker.repos.TickReader;
import com.depanker.ticker.services.DataLookUp;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataLookUpImpl implements DataLookUp {
    private final SqlParser sqlParser;
    private final TickReader tickReader;
    @SneakyThrows
    @Override
    public TickerCase getData(String query) {
        Select select = (Select) CCJSqlParserUtil.parse(query);
        PlainSelect ps = (PlainSelect) select.getSelectBody();
        Expression expr = null;
        if (ps.getWhere() != null) {
            expr = CCJSqlParserUtil.parseCondExpression(ps.getWhere().toString());
        }
        SelectColumns columns =  sqlParser.getSelectItems(select);
        WhereConditions whereConditions = sqlParser.getComparisonExpression(expr);
        String csvFileName =  sqlParser.getFileName(select);
        List<OrderByColumns> orderByColumns = sqlParser.getOrderByElements(ps);
        return tickReader.findData(csvFileName, columns, whereConditions, orderByColumns);
    }
}
