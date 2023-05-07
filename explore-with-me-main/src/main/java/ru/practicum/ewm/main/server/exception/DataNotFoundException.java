package ru.practicum.ewm.main.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
    private final LocalDateTime timestamp;

    public DataNotFoundException(String message) {
        super(message);
        this.timestamp = LocalDateTime.now();
    }

}
