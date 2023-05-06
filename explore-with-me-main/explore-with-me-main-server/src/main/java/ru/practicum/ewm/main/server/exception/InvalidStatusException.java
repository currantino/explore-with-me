package ru.practicum.ewm.main.server.exception;

public class InvalidStatusException extends BadRequestException {
    public InvalidStatusException(String message) {
        super(message);
    }
}
