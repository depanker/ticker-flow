package com.depanker.ticker.helper;


import com.depanker.ticker.exceptions.InvalidTickExceptions;
import lombok.SneakyThrows;

import java.util.Objects;

public class StringComparator {

    @SneakyThrows
    public static boolean evaluateExpressions(String a, String b, String operator) {
        boolean matcher = false;

        switch (operator) {
            case "=":
                matcher = Objects.equals(a, b);
                break;
            case "!=":
                matcher = !Objects.equals(a, b);
                break;
            default:
                throw new InvalidTickExceptions(String.format("Not a valid operator %s is not valid. " +
                        "Supported operations for numbers ('=', '!=').", operator));
        }
        return matcher;
    }
}
