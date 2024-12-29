package com.hellspawn287.user.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hellspawn287.user.validator.PasswordValid;
import com.hellspawn287.user.validator.groups.CreateUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@PasswordValid(groups = CreateUser.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserFullDto(UUID id,
                          @NotBlank(message = "Username can not be blank") String userName,
                          @NotBlank(message = "Email can not be blank") @Email(message = "Email is incorrect") String email,
                          @NotBlank(groups = CreateUser.class) String password,
                          @NotBlank(groups = CreateUser.class) String confirmPassword,
                          String hashedPassword, String creationDate,
                          String createdBy,
                          String lastModifiedDate,
                          String lastModifiedBy) {
}
