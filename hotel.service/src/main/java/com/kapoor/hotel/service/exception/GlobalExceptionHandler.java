package com.kapoor.hotel.service.exception;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

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
