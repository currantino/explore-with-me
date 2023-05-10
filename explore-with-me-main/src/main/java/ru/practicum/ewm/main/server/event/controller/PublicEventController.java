package ru.practicum.ewm.main.server.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.EventShortDto;
import ru.practicum.ewm.main.server.event.dto.EventSort;
import ru.practicum.ewm.main.server.event.dto.filter.EventFilterQuery;
import ru.practicum.ewm.main.server.event.service.PublicEventService;
import ru.practicum.ewm.stats.client.endpointhit.EndPointHitClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final PublicEventService eventService;
    private final EndPointHitClient endPointHitClient;

    @ResponseStatus(OK)
    @GetMapping("/{eventId}")
    public EventFullDto getEventById(
            @PathVariable(name = "eventId")
            Long eventId,
            HttpServletRequest request
    ) {
        endPointHitClient.createEndPointHit(request);
        return eventService.getEventByIdWithAcceptedComments(eventId);
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
            Integer size,
            HttpServletRequest request
    ) {
        endPointHitClient.createEndPointHit(request);
        EventFilterQuery query = EventFilterQuery.builder()
                .searchQuery(searchQuery)
                .categoryIds(categoryIds)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .build();
        return eventService.getEventsFiltered(query);
    }
}
