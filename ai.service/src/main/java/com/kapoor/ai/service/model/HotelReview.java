package com.kapoor.ai.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;

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

    private List<String> pros;

    private List<String> cons;

    private Double sentimentScore;

    private Integer totalReviews;

    private Double averageRating;

//    @Column(columnDefinition = "vector(768)")
//    @JdbcTypeCode(java.sql.Types.OTHER)
//    private List<Double> embedding;

    private UUID hotelId;
}