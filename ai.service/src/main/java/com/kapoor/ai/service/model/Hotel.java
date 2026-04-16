package com.kapoor.ai.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    private UUID id;

    private String location;

    private String name;

    private String about;

    private List<String> amenities;
}