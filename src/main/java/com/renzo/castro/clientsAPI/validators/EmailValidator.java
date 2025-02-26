package com.renzo.castro.clientsAPI.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && Pattern.matches(EMAIL_REGEX, value);
    }
}