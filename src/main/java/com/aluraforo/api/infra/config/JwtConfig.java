package com.aluraforo.api.infra.config;

import com.aluraforo.api.infra.security.JwtService;   // <- OJO: security, no service
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public JwtService jwtService(@Value("${api.security.token.secret}") String secret) {
        return new JwtService(secret);
    }
}