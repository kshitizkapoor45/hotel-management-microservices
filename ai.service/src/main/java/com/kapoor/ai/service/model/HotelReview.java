package com.kapoor.ai.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "text")
    private String summary;

    @Column(columnDefinition = "jsonb")
    private String pros;

    @Column(columnDefinition = "jsonb")
    private String cons;

    private Double sentimentScore;

    private Integer totalReviews;

    private Double averageRating;

    private UUID hotelId;
}