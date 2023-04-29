package ru.practicum.ewm.main.server.location.dto;

import lombok.Value;

@Value
public class LocationDto {
    Long id;
    Double lat;
    Double lon;
}