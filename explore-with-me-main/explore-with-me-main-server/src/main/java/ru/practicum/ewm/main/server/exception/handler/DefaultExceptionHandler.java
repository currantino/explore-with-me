package ru.practicum.ewm.main.server.exception.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.ewm.main.server.exception.DataNotFoundException;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice(basePackages = "ru.practicum.ewm.main.server")
public class DefaultExceptionHandler {
    @ExceptionHandler()
    @ResponseStatus(NOT_FOUND)
    public ApiError handleDataNotFoundException(final DataNotFoundException e) {
        return ApiError.builder()
                .reason("The requested resource does not exist.")
                .message(e.getMessage())
                .timestamp(e.getTimestamp())
                .status(NOT_FOUND)
                .build();
    }

    @ExceptionHandler()
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleDataNotFoundException(final MethodArgumentNotValidException e) {
        return ApiError.builder()
                .reason("The received request is invalid.")
                .message(e.getMessage())
                .timestamp(now())
                .status(NOT_FOUND)
                .build();
    }

    @ExceptionHandler()
    @ResponseStatus(CONFLICT)
    public ApiError handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        return ApiError.builder()
                .reason("Conflict with existing resources.")
                .message(e.getMessage())
                .timestamp(now())
                .status(CONFLICT)
                .build();
    }
}
