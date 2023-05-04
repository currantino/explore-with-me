package ru.practicum.ewm.main.server.exception;

public class EventAlreadyPublishedException extends ru.practicum.ewm.main.server.exception.ConflictException {
    public EventAlreadyPublishedException(String message) {
        super(message);
    }
}
