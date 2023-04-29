package ru.practicum.ewm.main.server.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.event.dto.CreateEventRequestDto;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.service.EventService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/events")
    public EventFullDto createEvent(
            @PathVariable(name = "userId")
            Long userId,
            @Valid
            @RequestBody
            CreateEventRequestDto eventDto
    ) {
        return eventService.createEvent(userId, eventDto);
    }

}
