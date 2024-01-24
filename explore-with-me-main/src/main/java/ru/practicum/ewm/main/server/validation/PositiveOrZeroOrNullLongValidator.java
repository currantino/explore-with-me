package ru.practicum.ewm.main.server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveOrZeroOrNullLongValidator implements ConstraintValidator<PositiveOrZeroOrNull, Long> {
    @Override
    public boolean isValid(
            Long value,
            ConstraintValidatorContext context
    ) {
        return value == null || value > -1;
    }
}
