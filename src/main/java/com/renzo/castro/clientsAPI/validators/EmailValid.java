package com.renzo.castro.clientsAPI.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface EmailValid {
    String message() default "El correo electrónico no es válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
