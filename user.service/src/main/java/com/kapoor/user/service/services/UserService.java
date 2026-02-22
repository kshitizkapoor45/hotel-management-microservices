package com.kapoor.user.service.services;

import com.kapoor.user.service.config.HotelService;
import com.kapoor.user.service.config.RatingService;
import com.kapoor.user.service.dto.Hotel;
import com.kapoor.user.service.dto.Rating;
import com.kapoor.user.service.entities.User;
import com.kapoor.user.service.exception.CustomException;
import com.kapoor.user.service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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

    public User saveUser(User user){
        User u = userRepository.save(user);
        return u;
    }

    public User getUserById(UUID id){
        User u = userRepository.findById(id).orElseThrow(() ->
                new CustomException("User not found","404"));

        List<Rating> list = ratingService.getRatingsOfUser(u.getId());
        List<Rating> ratingList = list.stream()
                .map(l -> {
                    Hotel hotel = hotelService.getHotel(l.getHotelId());
                    l.setHotel(hotel);
                    return l;
                }).collect(Collectors.toList());
        u.setRatings(ratingList);
        return u;
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
}