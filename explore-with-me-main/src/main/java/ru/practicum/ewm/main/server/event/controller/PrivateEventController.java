package ru.practicum.ewm.main.server.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.event.dto.*;
import ru.practicum.ewm.main.server.event.service.PrivateEventService;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PrivateEventController {
    private final PrivateEventService eventService;

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

    @ResponseStatus(OK)
    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEventsByUserId(
            @PathVariable(name = "userId")
            Long userId,
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
        return eventService.getEventsByUserId(
                userId,
                from,
                size
        );
    }

    @ResponseStatus(OK)
    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventByUserIdAndEventId(
            @PathVariable(name = "userId")
            Long userId,
            @PathVariable(name = "eventId")
            Long eventId
    ) {
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @ResponseStatus(OK)
    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto patchEvent(
            @PathVariable(name = "eventId")
            Long eventId,
            @PathVariable(name = "userId")
            Long userId,
            @Valid
            @RequestBody
            UpdateEventRequestDto updateDto
    ) {
        return eventService.patchEvent(
                eventId,
                userId,
                updateDto
        );
    }

    @ResponseStatus(OK)
    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getParticipationRequestsByEventId(
            @PathVariable(name = "userId")
            Long userId,
            @PathVariable(name = "eventId")
            Long eventId
    ) {
        return eventService.getParticipationRequestsByEventId(userId, eventId);
    }

    @ResponseStatus(OK)
    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateParticipationRequestStatus(
            @PathVariable(name = "userId")
            Long userId,
            @PathVariable(name = "eventId")
            Long eventId,
            @RequestBody
            EventStatusUpdateRequest request
    ) {
        return eventService.updateParticipationRequestStatus(
                userId,
                eventId,
                request
        );
    }


}
