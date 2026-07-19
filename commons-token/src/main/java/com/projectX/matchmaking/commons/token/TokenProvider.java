package com.projectX.matchmaking.commons.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class TokenProvider {
    private final String jwtSecret;

    // The secret is passed in via the library configuration
    public TokenProvider(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    // This handles the signing key creation (replacing your original signingKey() method)
    public SecretKey getSigningKey() {
        byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateAppToken(Long userId, String role, String type, Duration validity) {
        Instant now    = Instant.now();
        Instant expiry = now.plus(validity);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())   // jti — unique token id
                .subject(userId.toString())          // who this token belongs to
                .claim("role", role)               // app-level role
                .claim("type", type)                // distinguishes from memberToken
                .issuedAt(Date.from(now))            // iat
                .expiration(Date.from(expiry))       // exp
                .signWith(getSigningKey())              // HMAC-SHA256 signature
                .compact();                          // produces the final string
    }

}
