package com.hellspawn287.user.security.controller;

import com.hellspawn287.user.model.dto.LoginDto;
import com.hellspawn287.user.model.dto.TokenDto;
import com.hellspawn287.user.security.service.SecurityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;
//InterfaceFilter
    @PostMapping("/login")
    public TokenDto login(@RequestBody @Valid LoginDto loginDto) {
        return new TokenDto(securityService.login(loginDto));
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
        securityService.logout(token);
    }
}