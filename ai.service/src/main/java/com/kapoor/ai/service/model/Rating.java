package com.kapoor.ai.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private UUID id;

    private UUID userId;

    private UUID hotelId;

    private Integer rating;

    private String feedback;
}
