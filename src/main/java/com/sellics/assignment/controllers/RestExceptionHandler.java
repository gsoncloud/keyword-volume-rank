package com.sellics.assignment.controllers;

import com.sellics.assignment.dto.ErrorDetail;
import com.sellics.assignment.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorDetail> handleBusinessException(
            BusinessException ex) {
        ErrorDetail error = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return new ResponseEntity(error, error.getStatus());
    }

}
