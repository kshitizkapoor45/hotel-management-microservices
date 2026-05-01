package com.kapoor.ratings.controller;

import com.kapoor.ratings.Response;
import com.kapoor.ratings.dto.RatingRequest;
import com.kapoor.ratings.dto.RatingResponse;
import com.kapoor.ratings.model.Rating;
import com.kapoor.ratings.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rating/")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping("/register")
    public ResponseEntity<Response> saveHotel(@AuthenticationPrincipal Jwt jwt,@RequestBody RatingRequest request){
        String subject = jwt.getSubject();
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.save(subject,request));
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<List<Rating>> getByHotel(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getByHotel(id));
    }

    @GetMapping("/user")
    public ResponseEntity<List<RatingResponse>> getByUser(@AuthenticationPrincipal Jwt jwt){
        String subject = jwt.getSubject();
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getByUser(subject));
    }

    @GetMapping("/")
    public ResponseEntity<List<Rating>> getRatings(){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAll());
    }
}
