package com.hellspawn287.user.controller;

import com.hellspawn287.user.model.UserMapper;
import com.hellspawn287.user.model.dto.RegistrationDto;
import com.hellspawn287.user.model.dto.UserFullDto;
import com.hellspawn287.user.service.UserService;
import com.hellspawn287.user.validator.groups.CreateUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class RegistrationController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Validated(CreateUser.class)
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid RegistrationDto signUpDto) {
        userService.save(userMapper.mapRegistrationDtoToUser(signUpDto));
    }
}