package ru.practicum.ewm.main.server.event.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.EventShortDto;
import ru.practicum.ewm.main.server.event.dto.EventSort;
import ru.practicum.ewm.main.server.event.dto.filter.EventFilterQuery;
import ru.practicum.ewm.main.server.event.service.PublicEventService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQlPublicEventController {
    private final PublicEventService eventService;

    @QueryMapping
    public EventFullDto event(
            @Argument
            Long eventId
    ) {
        return eventService.getEventByIdWithAcceptedComments(eventId);
    }


    @QueryMapping
    public List<EventShortDto> getEventsPublic(
            @Argument
            String searchQuery,
            @Argument
            List<Long> categoryIds,
            @Argument
            Boolean paid,
            @Argument
            LocalDateTime rangeStart,
            @Argument
            LocalDateTime rangeEnd,
            @Argument
            Boolean onlyAvailable,
            @Argument
            EventSort sort,
            @Argument
            Integer from,
            @Argument
            Integer size
    ) {
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