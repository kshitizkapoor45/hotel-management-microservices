package com.kapoor.ai.service.dto;

import com.kapoor.ai.service.model.Hotel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelSearchResponse {
    private String hotelId;
    private Double score;
    private Hotel hotel;
}