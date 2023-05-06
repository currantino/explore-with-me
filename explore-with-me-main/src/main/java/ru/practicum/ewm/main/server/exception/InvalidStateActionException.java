package ru.practicum.ewm.main.server.exception;

public class InvalidStateActionException extends ConflictException {
    public InvalidStateActionException(String message) {
        super(message);
    }
}
