package com.depanker.ticker.services;

public interface ColumnValueTypeConverter {
    Object convert(String colName, String value) throws Exception;
}
