package com.kapoor.ai.service.controller;

import com.kapoor.ai.service.dto.HotelReviewDto;
import com.kapoor.ai.service.service.HotelReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/public")
public class AiController {
    private final HotelReviewService hotelReviewService;

    @GetMapping("/hotel-review/{hotelId}")
    public ResponseEntity<HotelReviewDto> generateHotelReview(@PathVariable UUID hotelId) {
        return ResponseEntity.ok(hotelReviewService.getHotelReviewSummary(hotelId));
    }
}