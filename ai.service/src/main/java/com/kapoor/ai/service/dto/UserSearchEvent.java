package com.kapoor.ai.service.dto;

import java.util.List;
import java.util.UUID;

public record UserSearchEvent(
        String userId,
        String query,
        List<String> hotelIds,
        List<String> hotelJson
) {
}