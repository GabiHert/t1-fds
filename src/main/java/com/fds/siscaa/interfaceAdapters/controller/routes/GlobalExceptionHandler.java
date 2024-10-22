package com.fds.siscaa.interfaceAdapters.controller.routes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fds.siscaa.domain.exception.CustomException;

import lombok.Getter;

@Getter
class ExceptionDto {
    private int statusCode;
    private String errorType;
    private String message;

    public ExceptionDto(CustomException ex) {
        this.statusCode = ex.getStatusCode();
        this.errorType = ex.getErrorType();
        this.message = ex.getMessage();
    }
}

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDto> handleCustomException(CustomException ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionDto(ex), HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionDto(CustomException.InternalServerError(ex.getMessage())),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
