package com.renzo.castro.clientsAPI.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface NameValid {
    String message() default "El nombre solo puede contener letras y espacios, y debe tener al menos 2 caracteres";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
