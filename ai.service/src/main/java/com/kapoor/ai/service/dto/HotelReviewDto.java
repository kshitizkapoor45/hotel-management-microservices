package com.kapoor.ai.service.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HotelReviewDto {
    private String summary;
    private List<String> pros;
    private List<String> cons;
    private Double sentimentScore;
}
