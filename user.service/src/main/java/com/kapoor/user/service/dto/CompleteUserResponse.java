package com.kapoor.user.service.dto;

import java.util.List;

public record CompleteUserResponse(
        UserResponse user,
        List<Rating> ratings
) {
}