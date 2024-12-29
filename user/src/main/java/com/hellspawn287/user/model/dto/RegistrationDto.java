package com.hellspawn287.user.model.dto;

import com.hellspawn287.user.validator.PasswordValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@PasswordValid
public record RegistrationDto(
    @NotBlank
    String username,
    @NotBlank @Email(message = "Email is incorrect")
    String email,
    @NotBlank(message = "Password is incorrect")
    String password,
    @NotBlank(message = "Password is incorrect")
    String confirmPassword
) {}
