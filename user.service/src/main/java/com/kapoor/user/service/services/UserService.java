package com.kapoor.user.service.services;

import com.kapoor.user.service.config.HotelService;
import com.kapoor.user.service.config.RatingService;
import com.kapoor.user.service.dto.CompleteUserResponse;
import com.kapoor.user.service.dto.Hotel;
import com.kapoor.user.service.dto.Rating;
import com.kapoor.user.service.dto.UserResponse;
import com.kapoor.user.service.entities.User;
import com.kapoor.user.service.exception.CustomException;
import com.kapoor.user.service.repositories.UserRepository;
import com.kapoor.user.service.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
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

    public CompleteUserResponse getUserById(UUID id) {
        UserResponse user = mapper.mapToUser(getUserCore(id));

        List<Rating> ratings = ratingService.getRatingsOfUser(id);
        ratings.forEach(r -> r.setHotel(
                hotelService.getHotel(r.getHotelId())
        ));
        return new CompleteUserResponse(user,ratings);
    }

    public UserResponse getUserWithoutRatings(UUID id) {
        return mapper.mapToUser(getUserCore(id));
    }

    private User getUserCore(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", "404"));
    }

    public List<UserResponse> getAll(){
        return userRepository.findAll().stream()
                .map(u -> mapper.mapToUser(u)).toList();
    }
}