package com.depanker.ticker.services.impl;

import com.depanker.ticker.exceptions.InvalidTickExceptions;
import com.depanker.ticker.services.ColumnValueTypeConverter;
import com.depanker.ticker.types.ColumnType;
import org.springframework.stereotype.Service;

@Service
public class ColumnValueTypeConverterImpl implements ColumnValueTypeConverter {
    @Override
    public Object convert(String colName, String value) throws Exception {
        Object result = null;
        switch (ColumnType.valueOf(colName)) {
            case TIMESTAMP:
                result =  Long.parseLong(value);
                break;
            case PRICE:
                result = Double.parseDouble(value);
                break;
            case CURRENCY:
                result = value;
                break;
            case CLOSE_PRICE:
                result = Double.parseDouble(value);
                break;
            default:
                throw new InvalidTickExceptions(String.format("Invalid column passed %s", value));
        }
        return result;
    }
}
