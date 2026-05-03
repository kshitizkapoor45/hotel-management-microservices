package com.kapoor.hotel.service.config;

import com.kapoor.hotel.service.dto.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    @GetMapping("/api/rating/public/hotel/{hotelId}")
    List<Rating> getRatingsOfHotel(@PathVariable UUID hotelId);
}