package ru.practicum.ewm.main.server.event.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class EventDateValidator implements ConstraintValidator<NotEarlierThanTwoHours, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        return eventDate == null || eventDate.isAfter(now().plusHours(2));
    }
}
