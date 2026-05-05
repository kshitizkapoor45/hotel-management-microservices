package com.kapoor.hotel.service.dto;

import com.kapoor.hotel.service.model.Hotel;

import java.util.List;

public record HotelRatingResponse(
        Hotel hotel,
        HotelReviewDto aiReview,
        List<Rating> ratings
) {
}