package ru.practicum.ewm.main.server.exception;

public class EventNotFoundException extends DataNotFoundException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
