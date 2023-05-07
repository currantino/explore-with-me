package ru.practicum.ewm.main.server.exceptionhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.ewm.main.server.exception.BadRequestException;
import ru.practicum.ewm.main.server.exception.ConflictException;
import ru.practicum.ewm.main.server.exception.DataNotFoundException;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice(basePackages = "ru.practicum.ewm.main.server")
public class DefaultExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ApiError> handleDataNotFoundException(final DataNotFoundException e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiError.builder()
                        .reason("The requested resource does not exist.")
                        .message(e.getMessage())
                        .timestamp(e.getTimestamp())
                        .status(NOT_FOUND)
                        .build());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ApiError> handleDataNotFoundException(final MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body(ApiError.builder()
                        .reason("The received request is invalid.")
                        .message(e.getMessage())
                        .timestamp(now())
                        .status(BAD_REQUEST)
                        .build());
    }

    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(ApiError.builder()
                        .reason("Data integrity was violated.")
                        .message(e.getMessage())
                        .timestamp(now())
                        .status(CONFLICT)
                        .build());
    }

    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public ResponseEntity<ApiError> handleConflictException(final ConflictException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(ApiError.builder()
                        .reason("Conflict with existing resources.")
                        .message(e.getMessage())
                        .timestamp(now())
                        .status(CONFLICT)
                        .build());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ApiError> handleBadRequestException(final BadRequestException e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ApiError.builder()
                        .reason("Invalid request.")
                        .message(e.getMessage())
                        .timestamp(now())
                        .status(BAD_REQUEST)
                        .build());
    }

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleRuntimeException(final RuntimeException e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ApiError.builder()
                        .reason("Unexpected error occurred!")
                        .message(e.getMessage())
                        .timestamp(now())
                        .status(INTERNAL_SERVER_ERROR)
                        .build());
    }
}
