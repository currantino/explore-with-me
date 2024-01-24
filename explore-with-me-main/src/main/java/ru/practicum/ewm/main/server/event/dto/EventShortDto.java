package ru.practicum.ewm.main.server.event.dto;

import lombok.Value;
import ru.practicum.ewm.main.server.category.dto.CategoryEventDto;
import ru.practicum.ewm.main.server.user.dto.read.UserShortDto;

import java.time.LocalDateTime;

@Value
public class EventShortDto {
    Long id;
    String annotation;
    CategoryEventDto category;
    Integer confirmedRequests;
    LocalDateTime eventDate;
    UserShortDto initiator;
    Boolean paid;
    String title;
    Long views;
}