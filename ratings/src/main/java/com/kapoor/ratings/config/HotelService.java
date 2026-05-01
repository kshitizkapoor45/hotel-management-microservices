package com.kapoor.ratings.config;

import com.kapoor.ratings.model.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(
        name = "HOTEL-SERVICE",
        configuration = FeignConfig.class
)
public interface HotelService {

    @GetMapping("/api/hotel/{hotelId}")
    Hotel getHotel(@PathVariable UUID hotelId);
}