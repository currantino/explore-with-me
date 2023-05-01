package ru.practicum.ewm.main.server.participationrequest.entity;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestStatus;

import java.time.LocalDateTime;

@Value
@Builder
public class ParticipationRequestDto {
    Long id;
    LocalDateTime created;
    Long event;
    Long requester;
    ParticipationRequestStatus status;
}