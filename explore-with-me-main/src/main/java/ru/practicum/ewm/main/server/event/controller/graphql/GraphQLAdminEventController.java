package ru.practicum.ewm.main.server.event.controller.graphql;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.UpdateEventAdminRequestDto;
import ru.practicum.ewm.main.server.event.dto.filter.AdminEventFilterQuery;
import ru.practicum.ewm.main.server.event.entity.state.EventState;
import ru.practicum.ewm.main.server.event.service.AdminEventService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLAdminEventController {
    private static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final AdminEventService adminEventService;

    @QueryMapping
    public List<EventFullDto> getEvents(
            @Argument
            List<Long> userIds,
            @Argument
            List<EventState> states,
            @Argument
            List<Long> categoryIds,
            @Argument
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT_PATTERN)
            LocalDateTime rangeStart,
            @Argument
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT_PATTERN)
            LocalDateTime rangeEnd,
            @Argument
            Integer from,
            @Argument
            Integer size
    ) {
        AdminEventFilterQuery filterQuery = AdminEventFilterQuery.builder()
                .userIds(userIds)
                .states(states)
                .categoryIds(categoryIds)
                .rangeStart(rangeStart)
                .from(from)
                .size(size)
                .rangeEnd(rangeEnd)
                .build();
        return adminEventService.getEvents(filterQuery);
    }

    @MutationMapping
    public EventFullDto patchEventAdmin(
            @Argument
            Long eventId,
            @Argument
            UpdateEventAdminRequestDto eventDto
    ) {
        return adminEventService.patchEvent(eventId, eventDto);
    }
}
