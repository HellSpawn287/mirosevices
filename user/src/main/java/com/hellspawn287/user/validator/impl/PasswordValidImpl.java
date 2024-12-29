package com.hellspawn287.user.validator.impl;

import com.hellspawn287.user.model.dto.RegistrationDto;
import com.hellspawn287.user.model.dto.UserFullDto;
import com.hellspawn287.user.validator.PasswordValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidImpl implements ConstraintValidator<PasswordValid, RegistrationDto> {
    @Override
    public boolean isValid(RegistrationDto value, ConstraintValidatorContext context) {
        if (value.confirmPassword() == null) {
            return false;
        }
        return value.confirmPassword().equals(value.password());
    }
}