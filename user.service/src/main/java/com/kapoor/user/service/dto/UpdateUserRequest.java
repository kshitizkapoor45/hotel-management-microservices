package com.kapoor.user.service.dto;

public record UpdateUserRequest(
        String name,
        String location,
        String mobileNumber
) {
}