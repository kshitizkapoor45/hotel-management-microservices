package com.kapoor.ratings.service;

import com.kapoor.ratings.Response;
import com.kapoor.ratings.model.Rating;
import com.kapoor.ratings.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public Response save(Rating hotel){
        ratingRepository.save(hotel);
        return new Response("Rating saved successfully");
    }

    public List<Rating> getAll(){
        return ratingRepository.findAll();
    }

    public List<Rating> getByUser(UUID id){
        return ratingRepository.findByUserId(id);
    }

    public List<Rating> getByHotel(UUID id){
        return ratingRepository.findByHotelId(id);
    }
}
