package com.kapoor.ai.service.service;

import com.kapoor.ai.service.repository.HotelReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {
    private final HotelReviewRepository hotelReviewRepository;
}