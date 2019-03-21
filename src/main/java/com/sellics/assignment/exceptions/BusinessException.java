package com.sellics.assignment.exceptions;

public class BusinessException extends RuntimeException {

    private String message;

    public BusinessException(String message, Throwable exception) {
        super(exception);
        this.message = message;
    }

    public BusinessException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}