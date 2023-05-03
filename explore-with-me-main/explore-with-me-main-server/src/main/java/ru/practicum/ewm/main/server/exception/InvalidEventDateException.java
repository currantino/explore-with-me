package ru.practicum.ewm.main.server.exception;

public class InvalidEventDateException extends ConflictException {
    public InvalidEventDateException(String message) {
        super(message);
    }
}
