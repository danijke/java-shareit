package ru.practicum.shareit.validation;

import jakarta.validation.*;

import java.lang.annotation.*;

@Constraint(validatedBy = StartBeforeEndValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StartBeforeEnd {
    String message() default "start must be before end";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
