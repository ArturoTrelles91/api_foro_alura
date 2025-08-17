package com.aluraforo.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final Algorithm algorithm;

    public JwtService(@Value("${api.security.token.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(String subject) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(2, ChronoUnit.HOURS)))
                .sign(algorithm);
    }

    /** Devuelve el username si es válido; si no, null (no lanza excepción). */
    public String validateAndGetSubject(String token) {
        try {
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
