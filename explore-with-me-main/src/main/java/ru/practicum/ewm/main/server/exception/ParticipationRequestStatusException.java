package ru.practicum.ewm.main.server.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class ParticipationRequestStatusException extends ConflictException {
    public ParticipationRequestStatusException(String message) {
        super(message);
    }
}
