package ru.practicum.ewm.main.server.exception;

public class CategoryNotFoundException extends DataNotFoundException {
    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}
