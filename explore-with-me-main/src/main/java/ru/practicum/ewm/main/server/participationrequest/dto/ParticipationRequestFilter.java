package ru.practicum.ewm.main.server.participationrequest.dto;

import lombok.Value;

@Value
public class ParticipationRequestFilter {
    Long requesterId;
    Long eventId;
    ParticipationRequestStatus status;
}
