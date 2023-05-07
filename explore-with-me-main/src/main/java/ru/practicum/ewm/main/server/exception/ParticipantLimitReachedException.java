package ru.practicum.ewm.main.server.exception;

public class ParticipantLimitReachedException extends ConflictException {
    public ParticipantLimitReachedException(String message) {
        super(message);
    }
}
