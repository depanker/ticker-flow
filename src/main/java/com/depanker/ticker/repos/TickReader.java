package com.depanker.ticker.repos;

import com.depanker.ticker.beans.*;
import com.depanker.ticker.helper.DecimalComparator;
import com.depanker.ticker.helper.LongComparator;
import com.depanker.ticker.helper.StringComparator;
import com.depanker.ticker.types.ColumnType;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

import static com.depanker.ticker.types.ColumnType.*;

@Repository
@Data
public class TickReader {
    @Value("${csv.directory:}")
    private String fileDirectory;
    private CsvMapper csvMapper;
    private CsvSchema csvSchema;
    Set<String> acceptableColumns = new HashSet<>(Arrays.asList("TIMESTAMP", "PRICE", "CLOSE_PRICE", "CURRENCY", "RIC"));

    public TickReader() {
        csvMapper = new CsvMapper();
        csvSchema = CsvSchema.emptySchema().withHeader();
    }

    @SneakyThrows
    public TickerCase findData(String csvFileName, SelectColumns columns, WhereConditions whereConditions, List<OrderByColumns> orderByColumns) {
        ObjectReader oReader = csvMapper.readerFor(Ticker.class).with(csvSchema);
        List<Ticker> tickers =  new ArrayList<>();
        try (Reader reader = new FileReader(fileDirectory+csvFileName+".csv")) {
            MappingIterator<Ticker> mi = oReader.readValues(reader);
            while (mi.hasNext()) {
                Ticker ticker =  mi.next();
                if (valid(ticker, whereConditions)) {
                    tickers.add(ticker);
                }
            }
        }
        if (!tickers.isEmpty() && !columns.getColumn().isEmpty()) {
            tickers =  tickers.stream().map(ticker -> removeUnRequiredFiled(columns.getColumn(), ticker))
                    .collect(Collectors.toList());

        }
        if (!tickers.isEmpty() && !orderByColumns.isEmpty()) {
            Collections.sort(tickers, comparator(orderByColumns));
        }

        return new TickerCase(tickers, System.currentTimeMillis());
    }

    private Comparator comparator(List<OrderByColumns> orderByColumns) {
        return (Comparator<Ticker>) (t1, t2) -> {
            int cmp = 0;
            if (t1 == null || t2 == null) {
                return cmp;
            }
            for (OrderByColumns orderByColumn :
                    orderByColumns) {
                if (TIMESTAMP.name().equalsIgnoreCase(orderByColumn.getColumnsName().toUpperCase())) {
                    if (t1.getTimestamp() != null && t2.getTimestamp() != null) {
                        cmp = t1.getTimestamp().compareTo(t2.getTimestamp());
                    }
                }
                if (PRICE.name().equalsIgnoreCase(orderByColumn.getColumnsName().toUpperCase())) {
                    if (t1.getPrice() != null && t2.getPrice() != null) {
                        cmp = t1.getPrice().compareTo(t2.getPrice());
                    }
                }
                if (CLOSE_PRICE.name().equalsIgnoreCase(orderByColumn.getColumnsName().toUpperCase())) {
                    if (t1.getClosePrice() != null && t2.getClosePrice() != null) {
                        cmp = t1.getClosePrice().compareTo(t2.getClosePrice());
                    }
                }
                if (CURRENCY.name().equalsIgnoreCase(orderByColumn.getColumnsName().toUpperCase())) {
                    cmp = t1.getCurrency().compareTo(t2.getCurrency());
                }
                if (orderByColumn.isDecending()) {
                    cmp *= -1;
                }
                if (cmp != 0) {
                    return cmp;
                }
            }
            return cmp;
        };
    }

    @SneakyThrows
    private boolean valid(Ticker ticker, WhereConditions whereConditions) {
        boolean isValid = true;
        if (!whereConditions.getWhereConditionList().isEmpty()) {
            for (WhereCondition condition :
                    whereConditions.getWhereConditionList()) {
                if (!isValid) {
                    break;
                }
                switch (condition.getColumn()) {
                    case CLOSE_PRICE:
                        isValid = DecimalComparator.evaluateExpressions((Double) condition.getValue(),
                                ticker.getClosePrice(), condition.getOperator()) && isValid;
                        break;
                    case PRICE:
                        isValid = DecimalComparator.evaluateExpressions((Double) condition.getValue(),
                                ticker.getPrice(), condition.getOperator()) && isValid;
                        break;
                    case CURRENCY:
                        isValid = StringComparator.evaluateExpressions((String)condition.getValue(),
                                ticker.getCurrency(), condition.getOperator()) && isValid;
                    case TIMESTAMP:
                        isValid = LongComparator.evaluateExpressions((Long) condition.getValue(),
                                ticker.getTimestamp(), condition.getOperator()) && isValid;
                        break;
                }
            }
        }
        return isValid;
    }

    private Ticker removeUnRequiredFiled(List<String> columnNames, Ticker ticker) {
        if (!columnNames.contains(TIMESTAMP.name())) {
            ticker.setTimestamp(null);
        }
        if (!columnNames.contains(ColumnType.PRICE.name())) {
            ticker.setPrice(null);
        }
        if (!columnNames.contains(ColumnType.CLOSE_PRICE.name())) {
            ticker.setClosePrice(null);
        }
        if (columnNames.contains(ColumnType.CURRENCY.name())) {
            ticker.setCurrency(null);
        }
        return ticker;
    }
}
