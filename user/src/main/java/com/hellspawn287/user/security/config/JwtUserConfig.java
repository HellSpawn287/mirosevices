package com.hellspawn287.user.security.config;

import com.hellspawn287.auth.JwtConfig;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtUserConfig {

    private final JwtConfig jwtConfig;

    public JwtUserConfig(@Value("${jwt.keys.private}") RSAPrivateKey rsaPrivateKey, @Value("${jwt.keys.public}") RSAPublicKey rsaPublicKey) {
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