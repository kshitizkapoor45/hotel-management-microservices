package com.kapoor.user.service.exception;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String errorDescription;
    private String code;
}