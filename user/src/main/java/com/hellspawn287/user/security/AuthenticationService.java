package com.hellspawn287.user.security;

import com.hellspawn287.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    public boolean hasAccessToUser(UUID userId){
        String currentUserEmail = SecurityUtils.getCurrentUserEmail().orElseThrow();

        return userService.getById(userId).getEmail().equals(currentUserEmail);
    }
}
