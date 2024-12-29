package com.hellspawn287.user.security.service;

import com.hellspawn287.user.model.Token;
import com.hellspawn287.user.model.dto.LoginDto;
import com.hellspawn287.user.security.repository.TokenRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@Slf4j
public record SecurityService(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, TokenRepository tokenRepository) {

    public String login(LoginDto loginDto) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                    .subject(authentication.getName()) // NAZWA UŻYTKOWNIKA
                    .expiresAt(Instant.now().plusSeconds(24 * 60 * 60))
                    .claim("scope", authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(" ")))
                    .build();


        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public void logout(String token) {
        tokenRepository.save(Token.builder().token(token).build());
    }
}
