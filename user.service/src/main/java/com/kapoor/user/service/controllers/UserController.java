package com.kapoor.user.service.controllers;

import com.kapoor.user.service.entities.User;
import com.kapoor.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "fallbackRatingHotel")
    public ResponseEntity<User> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    public ResponseEntity<User> fallbackRatingHotel(UUID id, Throwable ex) {
        User user = userService.getUserWithoutRatings(id);
        log.warn("Fallback triggered for getById [{}]: {}", id, ex.getMessage());

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(user);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> saveUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
}
