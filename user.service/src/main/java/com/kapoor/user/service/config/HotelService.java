package com.kapoor.user.service.config;

import com.kapoor.user.service.dto.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/api/hotel/{hotelId}")
    Hotel getHotel(@PathVariable UUID hotelId);
}