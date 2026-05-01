package com.kapoor.ratings.dto;

import com.kapoor.ratings.model.Hotel;
import com.kapoor.ratings.model.Rating;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponse {
    private Rating rating;
    private Hotel hotel;
}