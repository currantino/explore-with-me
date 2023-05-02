package ru.practicum.ewm.main.server.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.EventShortDto;
import ru.practicum.ewm.main.server.event.dto.EventSort;
import ru.practicum.ewm.main.server.event.service.PublicEventService;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final PublicEventService eventService;

    @ResponseStatus(OK)
    @GetMapping("/{eventId}")
    public EventFullDto getEventById(
            @PathVariable(name = "eventId")
            Long eventId
    ) {
        return eventService.getEventById(eventId);
    }


    @ResponseStatus(OK)
    @GetMapping
    public List<EventShortDto> getFilteredEvents(
            @RequestParam(
                    name = "text",
                    required = false
            )
            String searchQuery,
            @RequestParam(
                    name = "categories",
                    required = false
            )
            List<Long> categoryIds,
            @RequestParam(
                    name = "paid",
                    required = false
            )
            Boolean paid,
            @RequestParam(
                    name = "rangeStart",
                    required = false
            )
            LocalDateTime rangeStart,
            @RequestParam(
                    name = "rangeEnd",
                    required = false
            )
            LocalDateTime rangeEnd,
            @RequestParam(
                    name = "onlyAvailable",
                    defaultValue = "false"
            )
            Boolean onlyAvailable,
            @RequestParam(
                    name = "sort",
                    defaultValue = "EVENT_DATE"
            )
            EventSort sort,
            @RequestParam(
                    name = "from",
                    defaultValue = "0"
            )
            Integer from,
            @RequestParam(
                    name = "size",
                    defaultValue = "10"
            )
            Integer size
    ) {
        return eventService.getEventsFiltered(
                searchQuery,
                categoryIds,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size
        );
    }
}
