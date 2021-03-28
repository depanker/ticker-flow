package com.depanker.ticker.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SelectColumns implements SqlParts {
    private List<String> column;
}
