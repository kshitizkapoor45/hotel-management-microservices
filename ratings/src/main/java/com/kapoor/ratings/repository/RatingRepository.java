package com.kapoor.ratings.repository;

import com.kapoor.ratings.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, UUID> {

    List<Rating> findByUserId(UUID id);

    List<Rating> findByHotelId(UUID id);
}
