package ru.practicum.ewm.main.server.exception;

public class CommentNotFoundException extends DataNotFoundException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
