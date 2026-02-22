package com.kapoor.hotel.service.controller;

import com.kapoor.hotel.service.model.Hotel;
import com.kapoor.hotel.service.service.HotelService;
import com.kapoor.hotel.service.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel/")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/register")
    public ResponseEntity<Response> saveHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.saveHotel(hotel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotel(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Hotel>> getHotels(){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
    }
}
