package ru.practicum.ewm.main.server.event.dto;

import lombok.Value;
import ru.practicum.ewm.main.server.event.validation.NotEarlierThanTwoHours;
import ru.practicum.ewm.main.server.location.dto.LocationDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;


@Value
public class CreateEventRequestDto {
    @NotBlank(message = "Event annotation must not be blank.")
    String annotation;
    @NotNull(message = "Event category must not be null.")
    Long category;
    @NotBlank(message = "Event description must not be null.")
    String description;
    @NotNull(message = "Event date must not be null.")
    @NotEarlierThanTwoHours(message = "Event must not start in less than two hours.")
    LocalDateTime eventDate;
    @Valid
    @NotNull(message = "Event location must not be null.")
    LocationDto location;
    Boolean paid = false;
    @PositiveOrZero(message = "Event participant limit must be a positive integer or zero (no limit)")
    Integer participantLimit = 0;
    Boolean requestModeration;
    @NotBlank(message = "Event title must not be blank.")
    String title;
}