package ru.practicum.ewm.main.server.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.ewm.main.server.exception.DataNotFoundException;

@ControllerAdvice(basePackages = "ru.practicum.ewm.main.server")
public class DefaultExceptionHandler {
    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleDataNotFoundException(final DataNotFoundException e) {
        return ApiError.builder()
                .reason("The requested resource does not exist.")
                .message(e.getMessage())
                .timestamp(e.getTimestamp())
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
