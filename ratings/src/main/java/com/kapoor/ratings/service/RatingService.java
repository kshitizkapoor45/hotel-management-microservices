package com.kapoor.ratings.service;

import com.kapoor.ratings.Response;
import com.kapoor.ratings.config.HotelService;
import com.kapoor.ratings.dto.RatingRequest;
import com.kapoor.ratings.dto.RatingResponse;
import com.kapoor.ratings.model.Hotel;
import com.kapoor.ratings.model.Rating;
import com.kapoor.ratings.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RabbitTemplate rabbitTemplate;
    private final HotelService hotelService;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing.ratingEmbedding}")
    private String ratingKey;

    public Response save(String id,RatingRequest request){
        Rating r = Rating.builder()
                .rating(request.getRating())
                .hotelId(request.getHotelId())
                .feedback(request.getFeedback())
                .userId(id)
                .build();

        ratingRepository.save(r);
        rabbitTemplate.convertAndSend(exchange, ratingKey, r);
        return new Response("Rating saved successfully");
    }

    public List<Rating> getAll(){
        return ratingRepository.findAll();
    }

    public List<RatingResponse> getByUser(String id){
        List<Rating> ratings = ratingRepository.findByUserId(id);
        List<RatingResponse> response = ratings.stream()
                .map(r -> {
                    Hotel hotel = hotelService.getHotel(r.getHotelId());
                    return new RatingResponse(r,hotel);
                }).toList();

        return response;
    }

    public List<Rating> getByHotel(UUID id){
        return ratingRepository.findByHotelId(id);
    }
}