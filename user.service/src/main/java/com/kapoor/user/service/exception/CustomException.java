package com.kapoor.user.service.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException{
    private String message;
    private String code;

    public CustomException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}