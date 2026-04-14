package com.kapoor.ai.service.service;

import com.kapoor.ai.service.dto.HotelReviewDto;
import com.kapoor.ai.service.repository.HotelReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelReviewService {
    private final HotelReviewRepository hotelReviewRepository;

    public HotelReviewDto getHotelReviewSummary(UUID hotelId) {
        return hotelReviewRepository.findByHotelId(hotelId)
                .map(review -> new HotelReviewDto(
                        review.getSummary(),
                        review.getPros(),
                        review.getCons(),
                        review.getSentimentScore()
                ))
                .orElseThrow(() -> new RuntimeException("Hotel review not found"));
    }
}