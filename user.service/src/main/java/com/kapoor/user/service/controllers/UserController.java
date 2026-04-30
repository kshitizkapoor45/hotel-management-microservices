package com.kapoor.user.service.controllers;

import com.kapoor.user.service.dto.CompleteUserResponse;
import com.kapoor.user.service.dto.UpdateUserRequest;
import com.kapoor.user.service.dto.UserResponse;
import com.kapoor.user.service.entities.User;
import com.kapoor.user.service.services.UserService;
import com.kapoor.user.service.services.UserSyncService;
import com.kapoor.user.service.util.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal Jwt jwt) {
        String id = jwt.getSubject();
        UserResponse user = userService.getUserWithoutRatings(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/edit")
    public ResponseEntity<Response> editUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdateUserRequest request) {
        String id = jwt.getSubject();
        Response response = userService.editUser(id,request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "fallbackRatingHotel")
    public ResponseEntity<CompleteUserResponse> getById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    public ResponseEntity<UserResponse> fallbackRatingHotel(String id, Throwable ex) {
        UserResponse user = userService.getUserWithoutRatings(id);
        log.warn("Fallback triggered for getById [{}]: {}", id, ex.getMessage());

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(user);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> saveUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
}