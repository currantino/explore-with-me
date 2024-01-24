package ru.practicum.ewm.main.server.event.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.practicum.ewm.main.server.event.dto.*;
import ru.practicum.ewm.main.server.event.service.PrivateEventService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLPrivateEventController {
    private final PrivateEventService eventService;

    @MutationMapping
    public EventFullDto createEvent(
            @Argument
            Long userId,
            @Argument
            @Valid
            CreateEventRequestDto eventDto
    ) {
        return eventService.createEvent(userId, eventDto);
    }

    @QueryMapping
    public List<EventShortDto> getEventsByUserId(
            @Argument
            Long userId,
            @Argument
            Integer from,
            @Argument
            Integer size
    ) {
        return eventService.getEventsByUserId(
                userId,
                from,
                size
        );
    }

    @QueryMapping
    public EventFullDto getEventByInitiator(
            @Argument
            Long userId,
            @Argument
            Long eventId
    ) {
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @MutationMapping
    public EventFullDto patchEventPrivate(
            @Argument
            Long eventId,
            @Argument
            Long userId,
            @Argument
            @Valid
            UpdateEventRequestDto event
    ) {
        return eventService.patchEvent(
                eventId,
                userId,
                event
        );
    }

    @MutationMapping
    public EventRequestStatusUpdateResult updateParticipationRequestStatus(
            @Argument
            Long userId,
            @Argument
            Long eventId,
            @Argument
            @Valid
            EventStatusUpdateRequest request
    ) {
        return eventService.updateParticipationRequestStatus(
                userId,
                eventId,
                request
        );
    }


}
