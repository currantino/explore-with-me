package ru.practicum.ewm.main.server.event.dto;

import lombok.Value;
import ru.practicum.ewm.main.server.location.dto.LocationDto;
import ru.practicum.ewm.main.server.validation.NotBlankOrNull;
import ru.practicum.ewm.main.server.validation.PositiveOrZeroOrNull;

import javax.validation.Valid;
import java.time.LocalDateTime;


@Value
public class UpdateEventRequestDto {
    @NotBlankOrNull
    String annotation;
    @NotBlankOrNull
    String description;
    Long category;
    LocalDateTime eventDate;
    @Valid
    LocationDto location;
    Boolean paid;
    @PositiveOrZeroOrNull
    Integer participantLimit;
    Boolean requestModeration;
    @NotBlankOrNull
    String title;
    StateAction stateAction;
}