package com.aluraforo.api.infra.exceptions.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())         // APIs stateless
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                        // abretodo por ahora
                )
                .httpBasic(httpBasic -> {});          // o quítalo si no quieres basic
        return http.build();
    }
}
