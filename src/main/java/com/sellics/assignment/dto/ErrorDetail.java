package com.sellics.assignment.dto;

import org.springframework.http.HttpStatus;

public class ErrorDetail {

    private HttpStatus status;
    private String message;
    private String debugMessage;

    public ErrorDetail(HttpStatus status, Throwable ex) {
        this.status = status;
        this.message = ex.getMessage();
        this.debugMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
