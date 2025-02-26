package com.renzo.castro.clientsAPI.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Documented
public @interface PhoneValid {
    String message() default "Número de teléfono inválido, debe contener solo dígitos y tener entre 7 y 15 caracteres";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    }
