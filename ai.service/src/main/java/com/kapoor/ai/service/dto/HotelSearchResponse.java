package com.kapoor.ai.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelSearchResponse {
    private String hotelId;
    private Double score;
    private String content;
}