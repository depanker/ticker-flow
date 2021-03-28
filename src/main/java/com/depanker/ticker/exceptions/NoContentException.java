package com.depanker.ticker.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoContentException extends RuntimeException {
    final String message;
    public NoContentException(String message) {
        this.message = message;
    }
}
