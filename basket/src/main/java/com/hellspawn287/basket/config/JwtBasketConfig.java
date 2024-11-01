package com.hellspawn287.basket.config;

import com.hellspawn287.auth.JwtConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtBasketConfig {

    private final JwtConfig jwtConfig;

    public JwtBasketConfig(@Value("${jwt.keys.private}") RSAPrivateKey rsaPrivateKey,
                           @Value("${jwt.keys.public}") RSAPublicKey rsaPublicKey) {
        jwtConfig = new JwtConfig(rsaPrivateKey, rsaPublicKey);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return jwtConfig.jwtDecoder();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return jwtConfig.jwtEncoder();
    }
}
