package com.kapoor.user.service.config;

import com.kapoor.user.service.dto.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    @GetMapping("/api/rating/user/{userId}")
    List<Rating> getRatingsOfUser(@PathVariable UUID userId);
}
