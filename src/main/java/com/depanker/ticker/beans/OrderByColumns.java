package com.depanker.ticker.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderByColumns {
    private String columnsName;
    private boolean isDecending;
}
