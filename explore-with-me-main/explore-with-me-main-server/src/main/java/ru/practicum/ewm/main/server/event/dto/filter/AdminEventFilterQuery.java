package ru.practicum.ewm.main.server.event.dto.filter;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.main.server.event.entity.state.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class AdminEventFilterQuery {
    List<Long> userIds;
    List<EventState> states;
    List<Long> categoryIds;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Integer from;
    Integer size;
}
