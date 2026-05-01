package com.kapoor.ratings.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingRequest {
    private UUID hotelId;
    private Integer rating;
    private String feedback;
}