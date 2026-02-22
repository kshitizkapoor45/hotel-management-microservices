package com.kapoor.hotel.service.repository;

import com.kapoor.hotel.service.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {
}