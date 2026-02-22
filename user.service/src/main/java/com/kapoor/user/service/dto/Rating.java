package com.kapoor.user.service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    private UUID userId;

    private UUID hotelId;

    private Integer rating;

    private String feedback;

    private Hotel hotel;
}
