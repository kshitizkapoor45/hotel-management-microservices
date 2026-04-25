package com.kapoor.user.service.controllers;

import com.kapoor.user.service.dto.CompleteUserResponse;
import com.kapoor.user.service.dto.UserResponse;
import com.kapoor.user.service.entities.User;
import com.kapoor.user.service.services.UserService;
import com.kapoor.user.service.services.UserSyncService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserSyncService userSyncService;

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @GetMapping("/debug")
    public String debug(Authentication auth) {
        return auth.getClass().getName();
    }

    @GetMapping("/sync-test")
    public User syncTest(JwtAuthenticationToken auth) {
        return userSyncService.syncUser(auth.getToken());
    }

    @GetMapping("/roles")
    public Object roles(JwtAuthenticationToken auth) {
        return auth.getAuthorities();
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "fallbackRatingHotel")
    public ResponseEntity<CompleteUserResponse> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    public ResponseEntity<UserResponse> fallbackRatingHotel(UUID id, Throwable ex) {
        UserResponse user = userService.getUserWithoutRatings(id);
        log.warn("Fallback triggered for getById [{}]: {}", id, ex.getMessage());

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(user);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> saveUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
}