package com.kapoor.user.service.controllers;

import com.kapoor.user.service.entities.User;
import com.kapoor.user.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> saveUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
}
