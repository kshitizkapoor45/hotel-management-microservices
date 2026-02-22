package com.kapoor.user.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorDescription(ex.getMessage());
        errorResponse.setCode(ex.getCode());

        HttpStatus httpStatus = switch (ex.getCode()) {
            case "400" -> HttpStatus.BAD_REQUEST;
            case "404" -> HttpStatus.NOT_FOUND;
            case "401" -> HttpStatus.UNAUTHORIZED;
            case "409" -> HttpStatus.CONFLICT;
            case "429" -> HttpStatus.TOO_MANY_REQUESTS;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDescription("An unexpected error occurred: " + ex.getMessage())
                .code("500")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
