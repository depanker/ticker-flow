package com.depanker.ticker.beans;

import com.depanker.ticker.types.ColumnType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class WhereCondition implements SqlParts {
    private ColumnType column;
    private String operator;
    private Object value;
}
