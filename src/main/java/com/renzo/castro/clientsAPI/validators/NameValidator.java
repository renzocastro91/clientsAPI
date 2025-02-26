package com.renzo.castro.clientsAPI.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<NameValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]{2,50}$");
    }
}