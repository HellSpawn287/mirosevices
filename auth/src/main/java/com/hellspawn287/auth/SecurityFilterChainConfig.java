package com.hellspawn287.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

public class SecurityFilterChainConfig {

    public static HttpSecurity securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.anyRequest().permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable) // wyłączamy logowanie przy każdym zapytaniu
                .oauth2ResourceServer(
                        abstractHttpConfigurer -> abstractHttpConfigurer.jwt(Customizer.withDefaults()) // włączenie rozparsowywania tokenu jwt
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // nie przechowujemy informacji o zapytaniu. Uniemożliwianie pracy w kontekście poprzedniego użytkownika
                );
    }

    public static HttpSecurity securityFilterChain(HttpSecurity http, OncePerRequestFilter logoutFilter) throws Exception {

        return securityFilterChain(http).addFilterBefore(logoutFilter, BasicAuthenticationFilter.class);
    }
}
