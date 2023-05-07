package ru.practicum.ewm.main.server.event.dto;

import lombok.Value;
import ru.practicum.ewm.main.server.event.dto.stateaction.AdminEventStateAction;
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
    Integer participantLimit;
    Boolean requestModeration;
    String title;
    AdminEventStateAction stateAction;
}