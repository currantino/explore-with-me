package ru.practicum.ewm.main.server.event.dto;

import lombok.Value;
import ru.practicum.ewm.main.server.category.dto.CategoryEventDto;
import ru.practicum.ewm.main.server.comment.dto.CommentDto;
import ru.practicum.ewm.main.server.event.entity.state.EventState;
import ru.practicum.ewm.main.server.location.dto.LocationDto;
import ru.practicum.ewm.main.server.user.dto.read.ReadUserDto;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class EventFullDto {
    Long id;
    String annotation;
    CategoryEventDto category;
    Integer confirmedRequests;
    String description;
    LocalDateTime createdOn;
    LocalDateTime eventDate;
    ReadUserDto initiator;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    LocalDateTime publishedOn;
    Boolean requestModeration;
    EventState state;
    String title;
    Long views;
    List<CommentDto> comments;
}