package ru.practicum.ewm.main.server.location.dto;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Value
public class LocationDto {
    Long id;
    @Min(value = -90, message = "Latitude must be between -90 and 90.")
    @Max(value = 90, message = "Longitude must be between -90 and 90.")
    Double lat;
    @Min(value = -180, message = "Latitude must be between -180 and 180.")
    @Max(value = 180, message = "Longitude must be between -180 and 180.")
    Double lon;
}