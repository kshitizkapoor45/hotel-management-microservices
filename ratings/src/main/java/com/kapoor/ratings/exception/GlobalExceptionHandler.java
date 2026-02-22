package com.kapoor.ratings.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e){
        Map<String,Object> res = new LinkedHashMap<>();
        res.put("message",e.getMessage());
        res.put("code", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
