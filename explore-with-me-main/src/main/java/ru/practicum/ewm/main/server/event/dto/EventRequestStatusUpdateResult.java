package ru.practicum.ewm.main.server.event.dto;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestDto;

import java.util.List;

@Value
@Builder
public class EventRequestStatusUpdateResult {
    List<ParticipationRequestDto> confirmedRequests;
    List<ParticipationRequestDto> rejectedRequests;
}

