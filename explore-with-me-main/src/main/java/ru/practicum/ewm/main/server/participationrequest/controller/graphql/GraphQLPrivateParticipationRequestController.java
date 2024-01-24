package ru.practicum.ewm.main.server.participationrequest.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestFilter;
import ru.practicum.ewm.main.server.participationrequest.service.PrivateParticipationRequestService;

import java.util.List;

@Validated
@Controller
@RequiredArgsConstructor
public class GraphQLPrivateParticipationRequestController {
    private final PrivateParticipationRequestService participationRequestService;

    @MutationMapping
    public ParticipationRequestDto createParticipationRequest(
            @Argument
            Long requesterId,
            @Argument
            Long eventId
    ) {
        return participationRequestService.createParticipationRequest(requesterId, eventId);
    }


    @MutationMapping
    public ParticipationRequestDto cancelParticipationRequest(
            @Argument
            Long userId,
            @Argument
            Long requestId
    ) {
        return participationRequestService.cancelParticipationRequest(userId, requestId);
    }

    @QueryMapping
    public List<ParticipationRequestDto> participationRequests(
            @Argument
            Integer from,
            @Argument
            Integer size,
            @Argument
            ParticipationRequestFilter filter
    ) {
        return participationRequestService.getFiltered(
                from, size, filter
        );
    }

}
