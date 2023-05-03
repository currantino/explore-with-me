package ru.practicum.ewm.main.server.event.controller;

import lombok.Value;
import ru.practicum.ewm.main.server.event.dto.AdminStateAction;
import ru.practicum.ewm.main.server.location.dto.LocationDto;

import java.time.LocalDateTime;

@Value
public class UpdateEventAdminRequestDto {
    String annotation;
    Long categoryId;
    String description;
    LocalDateTime eventDate;
    LocationDto location;
    Boolean paid;
    Integer participantLimit = 0;
    Boolean requestModeration = true;
    String title;
    AdminStateAction stateAction;
}