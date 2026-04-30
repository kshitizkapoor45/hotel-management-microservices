package com.kapoor.user.service.services;

import com.kapoor.user.service.config.HotelService;
import com.kapoor.user.service.config.RatingService;
import com.kapoor.user.service.dto.*;
import com.kapoor.user.service.entities.User;
import com.kapoor.user.service.exception.CustomException;
import com.kapoor.user.service.repositories.UserRepository;
import com.kapoor.user.service.util.Mapper;
import com.kapoor.user.service.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final HotelService hotelService;
    private final RatingService ratingService;
    private final Mapper mapper;

    public User saveUser(User user){
        User u = userRepository.save(user);
        return u;
    }

    public Response editUser(String id, UpdateUserRequest request){
        User user = userRepository.findByKeycloakId(id)
                .orElseThrow(() -> new CustomException("User not found", "404"));

        user.setName(request.name());
        user.setMobileNumber(request.mobileNumber());
        user.setLocation(request.location());

        userRepository.save(user);
        return new Response("User updated successfully");
    }

    public CompleteUserResponse getUserById(String id) {
        UserResponse user = mapper.mapToUser(getUserCore(id));

//        List<Rating> ratings = ratingService.getRatingsOfUser(id);
//        ratings.forEach(r -> r.setHotel(
//                hotelService.getHotel(r.getHotelId())
//        ));
        List<Rating> ratings = new ArrayList<>();
        return new CompleteUserResponse(user,ratings);
    }

    public UserResponse getUserWithoutRatings(String id) {
        log.info("Keycloak ID {}",id);
        return mapper.mapToUser(getUserCore(id));
    }

    private User getUserCore(String id) {
        return userRepository.findByKeycloakId(id)
                .orElseThrow(() -> new CustomException("User not found", "404"));
    }

    public List<UserResponse> getAll(){
        return userRepository.findAll().stream()
                .map(u -> mapper.mapToUser(u)).toList();
    }
}