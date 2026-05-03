package com.kapoor.hotel.service.service;

import com.kapoor.hotel.service.config.RatingService;
import com.kapoor.hotel.service.dto.HotelRatingResponse;
import com.kapoor.hotel.service.dto.Rating;
import com.kapoor.hotel.service.model.Hotel;
import com.kapoor.hotel.service.repository.HotelRepository;
import com.kapoor.hotel.service.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RatingService ratingService;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing.hotelEmbedding}")
    private String hotelKey;

    public Response saveHotel(Hotel hotel){
        hotelRepository.save(hotel);
        rabbitTemplate.convertAndSend(exchange, hotelKey, hotel);
        return new Response("Hotel saved successfully");
    }

    public Response updateHotel(Hotel hotel){
        Hotel h = hotelRepository.findById(hotel.getId()).orElseThrow(() ->
                new RuntimeException("Hotel not found"));

        h.setAbout(hotel.getAbout());
        h.setImageUrl(hotel.getImageUrl());
        h.setName(hotel.getName());
        h.setLocation(hotel.getLocation());
        h.setAmenities(hotel.getAmenities());

        hotelRepository.save(h);
        return new Response("Hotel updated successfully");
    }

    public Hotel getHotel(UUID id){
        return hotelRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Hotel not found"));
    }

    public List<HotelRatingResponse> getAll(){
        List<HotelRatingResponse> hotels = hotelRepository.findAll().stream()
                .map(h -> {
                    List<Rating> ratingsOfHotel = ratingService.getRatingsOfHotel(h.getId());
                    return new HotelRatingResponse(h,ratingsOfHotel);
                }).toList();
        return hotels;
    }

    public List<Hotel> getRecommendedHotels(){
        return hotelRepository.findAll();
    }
}
