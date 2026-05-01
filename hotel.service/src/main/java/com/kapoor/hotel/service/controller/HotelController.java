package com.kapoor.hotel.service.controller;

import com.kapoor.hotel.service.model.Hotel;
import com.kapoor.hotel.service.service.FileService;
import com.kapoor.hotel.service.service.HotelService;
import com.kapoor.hotel.service.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel/")
public class HotelController {

    private final HotelService hotelService;
    private final FileService fileService;

    @Value("${file.upload-dir}")
    private String directory;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> saveHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.saveHotel(hotel));
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> editHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.updateHotel(hotel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotel(id));
    }

    @GetMapping("/public/all")
    public ResponseEntity<List<Hotel>> getHotels(){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<Hotel>> getRecommendedHotels(){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
    }

    @PostMapping("/file-upload")
    public ResponseEntity<Response> fileUpload(@RequestParam("file") MultipartFile file) {
        String url = fileService.urlOfFile(directory, file);
        Response response = new Response(url);
        return ResponseEntity.ok(response);
    }
}