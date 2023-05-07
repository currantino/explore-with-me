package ru.practicum.ewm.main.server.event.dto.filter;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.main.server.event.dto.EventSort;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class EventFilterQuery {
    String searchQuery;
    List<Long> categoryIds;
    Boolean paid;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Boolean onlyAvailable;
    EventSort sort;
    Integer from;
    Integer size;
}
