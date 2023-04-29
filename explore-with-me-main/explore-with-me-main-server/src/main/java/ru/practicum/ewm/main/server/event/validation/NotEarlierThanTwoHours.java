package ru.practicum.ewm.main.server.event.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EventDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEarlierThanTwoHours {
    String message() default "Event must not start in less than two hours.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
