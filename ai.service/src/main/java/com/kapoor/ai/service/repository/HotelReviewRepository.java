package com.kapoor.ai.service.repository;

import com.kapoor.ai.service.model.HotelReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelReviewRepository extends JpaRepository<HotelReview, UUID> {
}
