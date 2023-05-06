package ru.practicum.ewm.main.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAccessibleException extends RuntimeException {
    private final LocalDateTime timestamp;

    public NotAccessibleException(String message) {
        super(message);
        this.timestamp = now();
    }
}
