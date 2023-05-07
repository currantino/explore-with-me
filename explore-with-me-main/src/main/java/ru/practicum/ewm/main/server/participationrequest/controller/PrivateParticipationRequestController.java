package ru.practicum.ewm.main.server.participationrequest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.main.server.participationrequest.service.PrivateParticipationRequestService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class PrivateParticipationRequestController {
    private final PrivateParticipationRequestService participationRequestService;

    @ResponseStatus(CREATED)
    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto createParticipationRequest(
            @PathVariable(name = "userId")
            Long userId,
            @RequestParam(name = "eventId")
            Long eventId
    ) {
        return participationRequestService.createParticipationRequest(userId, eventId);
    }

    @ResponseStatus(OK)
    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getParticipationRequestsByParticipatorId(
            @PathVariable(name = "userId")
            Long participatorId
    ) {
        return participationRequestService.getParticipationRequestsByParticipatorId(participatorId);
    }

    @ResponseStatus(OK)
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(
            @PathVariable(name = "userId")
            Long userId,
            @PathVariable(name = "requestId")
            Long requestId
    ) {
        return participationRequestService.cancelParticipationRequest(userId, requestId);
    }

}
