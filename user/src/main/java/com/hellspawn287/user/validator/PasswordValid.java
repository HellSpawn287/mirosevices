package com.hellspawn287.user.validator;

import com.hellspawn287.user.validator.impl.PasswordValidImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidImpl.class)
public @interface PasswordValid {
    String message() default "Password should be the same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
