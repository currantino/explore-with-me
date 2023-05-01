package ru.practicum.ewm.main.server.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PositiveOrZeroOrNullValidator.class, PositiveOrZeroOrNullIntegerValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveOrZeroOrNull {
    String message() default "Value must be null or positive or zero.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
