package ru.practicum.ewm.main.server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveOrZeroOrNullIntegerValidator implements ConstraintValidator<PositiveOrZeroOrNull, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || value > -1;
    }
}
