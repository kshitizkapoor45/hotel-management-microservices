package com.kapoor.ai.service.controller;

import com.kapoor.ai.service.dto.HotelSearchResponse;
import com.kapoor.ai.service.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/public/hotel-search")
    public ResponseEntity<List<HotelSearchResponse>> searchHotels(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "key")String key){
        String id = Optional.ofNullable(jwt).map(Jwt::getSubject).orElse(null);
        List<HotelSearchResponse> search = hotelService.search(id,key);
        return ResponseEntity.ok(search);
    }

    @GetMapping("/hotel-recommendations")
    public ResponseEntity<List<HotelSearchResponse>> hotelRecommendations(@AuthenticationPrincipal Jwt jwt){
        String id = Optional.ofNullable(jwt).map(Jwt::getSubject).orElse(null);
        List<HotelSearchResponse> search = hotelService.recommendations(id);
        return ResponseEntity.ok(search);
     }
}