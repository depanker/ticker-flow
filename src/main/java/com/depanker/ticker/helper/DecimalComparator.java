package com.depanker.ticker.helper;

import com.depanker.ticker.exceptions.InvalidTickExceptions;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DecimalComparator {
    private static Set<String> nullCaseNumberOperator = new HashSet<>(Arrays.asList("=", ">=", "<=", "!="));

    @SneakyThrows
    public static  boolean evaluateExpressions(Double price, Double value, String operator) {
        boolean matcher = false;
        if (value == null || price == null) {
            if(nullCaseNumberOperator.contains(operator)) {
                if (operator.equals("!=")) {
                    matcher = !Objects.equals(price, value);
                } else {
                    matcher = Objects.equals(price, value);
                }
            }
        } else {
            switch (operator) {
                case "=":
                    matcher = Objects.equals(value, price);
                    break;
                case "<":
                    matcher = value < price;
                    break;
                case "<=":
                    matcher = value <= price;
                    break;
                case ">":
                    matcher = value > price;
                    break;
                case ">=":
                    matcher = value >= price;
                    break;
                case "!=":
                    matcher = !Objects.equals(value, price);
                    break;
                default:
                    throw new InvalidTickExceptions(String.format("Not a valid operator %s is not valid. " +
                            "Supported operations for numbers ('=', '<', '<=', '>', '>=', '!=').", operator));
            }
        }
        return matcher;
    }
}
