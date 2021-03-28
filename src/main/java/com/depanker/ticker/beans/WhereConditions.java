package com.depanker.ticker.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WhereConditions implements SqlParts {
    List<WhereCondition> whereConditionList;
}
