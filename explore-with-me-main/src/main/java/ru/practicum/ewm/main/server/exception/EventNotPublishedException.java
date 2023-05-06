package ru.practicum.ewm.main.server.exception;

public class EventNotPublishedException extends ConflictException {
    public EventNotPublishedException(String message) {
        super(message);
    }
}
