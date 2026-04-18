package com.kapoor.ai.service.controller;

import com.kapoor.ai.service.dto.HotelSearchResponse;
import com.kapoor.ai.service.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<List<HotelSearchResponse>> searchHotels(@RequestParam(name = "key")String key){
        List<HotelSearchResponse> search = hotelService.search(key);
        return ResponseEntity.ok(search);
    }
}