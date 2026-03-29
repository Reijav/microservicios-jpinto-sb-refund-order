package com.jpinto.orchestator.exceptions;

public class StopSagaException extends RuntimeException {
    public StopSagaException(String message, Exception ex) {
        super(message, ex);
    }
}
