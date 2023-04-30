package ru.practicum.ewm.main.server.event.controller;

import lombok.Value;
import ru.practicum.ewm.main.server.category.dto.CategoryEventDto;

import java.time.LocalDateTime;

@Value
public class EventShortDto {
    Long id;
    String annotation;
    CategoryEventDto category;
    Long confirmedRequests;
    LocalDateTime eventDate;
    UserShortDto initiator;
    Boolean paid;
    String title;
    Long views;
}