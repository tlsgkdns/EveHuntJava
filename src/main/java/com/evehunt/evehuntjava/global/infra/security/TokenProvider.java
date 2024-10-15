package com.evehunt.evehuntjava.global.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

@Service
public class TokenProvider {
    private final String secretKey;
    private final long expirationHours;
    private final String issuer;

    @NotNull
    public String createToken(@NotNull String userSpecification) {
        return Jwts.builder().signWith((Key)(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName())))
                .setSubject(userSpecification).setIssuer(this.issuer).setIssuedAt((Date) Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(this.expirationHours, (TemporalUnit) ChronoUnit.HOURS))).compact();
    }


    public String validateTokenAndGetSubject(@NotNull String token) {

        return ((Claims)Jwts.parserBuilder().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build().parseClaimsJws(token).getBody()).getSubject();
    }

    public TokenProvider(@Value("${auth.jwt.secret-key}") @NotNull String secretKey, @Value("${auth.jwt.expiration-hours}") long expirationHours, @Value("${auth.jwt.issuer}") @NotNull String issuer) {
        this.secretKey = secretKey;
        this.expirationHours = expirationHours;
        this.issuer = issuer;
    }
}
