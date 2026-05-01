package com.kapoor.ratings.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    private UUID id;

    private String location;

    private String name;

    private List<String> amenities = new ArrayList<>();

    private String imageUrl;

    private String about;
}
