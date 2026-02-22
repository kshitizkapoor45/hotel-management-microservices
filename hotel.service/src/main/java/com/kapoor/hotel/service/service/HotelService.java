package com.kapoor.hotel.service.service;

import com.kapoor.hotel.service.model.Hotel;
import com.kapoor.hotel.service.repository.HotelRepository;
import com.kapoor.hotel.service.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    public Response saveHotel(Hotel hotel){
        hotelRepository.save(hotel);
        return new Response("Hotel saved successfully");
    }

    public Hotel getHotel(UUID id){
        return hotelRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Hotel not found"));
    }

    public List<Hotel> getAll(){
        return hotelRepository.findAll();
    }
}
