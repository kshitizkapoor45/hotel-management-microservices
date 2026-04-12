package com.kapoor.ratings.service;

import com.kapoor.ratings.Response;
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

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchange;

    @Value("${spring.rabbitmq.routing.key}")
    private String routingKey;

    public Response save(Rating hotel){
        ratingRepository.save(hotel);
        rabbitTemplate.convertAndSend(exchange, routingKey, hotel);
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
