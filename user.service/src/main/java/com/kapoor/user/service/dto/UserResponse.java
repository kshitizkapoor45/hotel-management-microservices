package com.kapoor.user.service.dto;

public record UserResponse(
        String name,
        String email,
        String keycloakId
) {
}