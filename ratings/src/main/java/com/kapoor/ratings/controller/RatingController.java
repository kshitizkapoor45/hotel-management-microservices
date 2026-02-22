package com.kapoor.ratings.controller;

import com.kapoor.ratings.Response;
import com.kapoor.ratings.model.Rating;
import com.kapoor.ratings.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rating/")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    @PostMapping("/register")
    public ResponseEntity<Response> saveHotel(@RequestBody Rating hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.save(hotel));
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<List<Rating>> getByHotel(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getByHotel(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Rating>> getByUser(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getByUser(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Rating>> getRatings(){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAll());
    }
}
