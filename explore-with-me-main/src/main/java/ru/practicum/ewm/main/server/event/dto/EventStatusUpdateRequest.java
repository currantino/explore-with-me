package ru.practicum.ewm.main.server.event.dto;

import lombok.Value;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class EventStatusUpdateRequest {
    @NotEmpty(message = "List of request ids must not be empty.")
    List<Long> requestIds;
    @NotNull(message = "New status must not be null.")
    ParticipationRequestStatus status;
}
