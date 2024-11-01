package com.hellspawn287.basket.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

    public static Optional<String> getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication != null ? authentication.getName() : null;
        return Optional.ofNullable(authentication).map(Authentication::getName);
    }
}
