package com.kapoor.hotel.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String location;

    private String name;

    private List<String> amenities = new ArrayList<>();

    private String imageUrl;

    @Column(length = 2000)
    private String about;
}