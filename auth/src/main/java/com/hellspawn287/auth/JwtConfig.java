package com.hellspawn287.auth;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@RequiredArgsConstructor
public class JwtConfig {

    private final RSAPrivateKey rsaPrivateKey;
    private final RSAPublicKey rsaPublicKey;

    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(
                new RSAKey.Builder(rsaPublicKey)
                        .privateKey(rsaPrivateKey)
                        .build()
        )));
    }
}
