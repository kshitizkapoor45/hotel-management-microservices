package com.kapoor.hotel.service.config;

import com.kapoor.hotel.service.dto.HotelReviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "AI.SERVICE")
public interface AiService {

    @GetMapping("/api/ai/public/hotel-review/{id}")
    HotelReviewDto getAiSummary(@PathVariable UUID id);
}
