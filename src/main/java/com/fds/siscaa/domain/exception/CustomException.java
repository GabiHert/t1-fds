package com.fds.siscaa.domain.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private int statusCode;
    private String errorType;

    public CustomException(String message, int statusCode, String errorType) {
        super(message);
        this.statusCode = statusCode;
        this.errorType = errorType;
    }

    public static CustomException NotFound(String message) {
        return new CustomException(message, 404, "Not Found");
    }

    public static CustomException BadRequest(String message) {
        return new CustomException(message, 400, "Bad Request");
    }

    public static CustomException InternalServerError(String message) {
        return new CustomException(message, 500, "Internal Server Error");
    }

    public static CustomException Conflict(String message) {
        return new CustomException(message, 409, "Conflict");
    }

}
