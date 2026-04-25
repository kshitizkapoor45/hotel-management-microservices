package com.kapoor.user.service.controllers;

import com.kapoor.user.service.entities.User;
import com.kapoor.user.service.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }
}
