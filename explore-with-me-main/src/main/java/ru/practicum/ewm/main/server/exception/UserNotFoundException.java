package ru.practicum.ewm.main.server.exception;

public class UserNotFoundException extends DataNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
