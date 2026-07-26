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

    /**
     * appToken — sub is the userId, role is always USER, type is always APP.
     * Global identity token, used for all app-level (non-room-scoped) endpoints.
     */
    public String generateAppToken(Long userId, Duration validity) {
        return buildToken(userId, Role.USER, TokenType.APP, null, validity);
    }

    /**
     * memberToken — sub is the memberId, role is MEMBER, type is ROOM.
     * Scoped to a single room via the roomId claim; used for voting and viewing room state.
     */
    public String generateMemberToken(Long memberId, Long roomId, Duration validity) {
        return buildToken(memberId, Role.MEMBER, TokenType.ROOM, roomId, validity);
    }

    /**
     * hostToken — sub is the memberId, role is HOST, type is ROOM.
     * Scoped to a single room via the roomId claim; used for starting/closing sessions and member management.
     */
    public String generateHostToken(Long memberId, Long roomId, Duration validity) {
        return buildToken(memberId, Role.HOST, TokenType.ROOM, roomId, validity);
    }

    private String buildToken(Long subjectId, Role role, TokenType type, Long roomId, Duration validity) {
        Instant now = Instant.now();
        Instant expiry = now.plus(validity);

        var builder = Jwts.builder()
                .id(UUID.randomUUID().toString())      // jti — unique token id
                .subject(subjectId.toString())          // userId or memberId depending on type
                .claim("role", role.name())
                .claim("type", type.name());

        if (roomId != null) {
            builder.claim("roomId", roomId.toString());
        }

        return builder
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(getSigningKey())
                .compact();
    }
}
