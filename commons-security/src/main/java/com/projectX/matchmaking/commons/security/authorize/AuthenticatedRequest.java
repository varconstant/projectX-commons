package com.projectX.matchmaking.commons.security.authorize;

import org.springframework.security.core.Authentication;

import java.util.Map;

/**
 * Reads the (principal, details) shape JwtAuthenticationFilter (commons-filter) attaches to every
 * authenticated request: principal is the caller's id as a String (user id for APP tokens, member
 * id for ROOM tokens), details is a Map&lt;String, String&gt; carrying the token's other claims
 * (type, role, token, roomId, maxVotes, anonymityLevel — the last three only present when the
 * token actually carries them). Centralizes what used to be a copy-pasted detailsOf() per
 * controller plus scattered inline claim lookups.
 */
public final class AuthenticatedRequest {

    private AuthenticatedRequest() {
    }

    /** The authenticated caller's id — user id for APP tokens, member id for ROOM tokens. */
    public static Long callerId(Authentication auth) {
        return Long.parseLong((String) auth.getPrincipal());
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> detailsOf(Authentication auth) {
        Object details = auth.getDetails();
        return details instanceof Map ? (Map<String, String>) details : Map.of();
    }

    /** The caller's own raw JWT — used when forwarding it onward (e.g. room-manager → match-service). */
    public static String token(Authentication auth) {
        return detailsOf(auth).get("token");
    }

    public static String roomId(Authentication auth) {
        return detailsOf(auth).get("roomId");
    }

    /** Vote cap embedded in a ROOM token's claims, or 0 if the token doesn't carry one. */
    public static int maxVotes(Authentication auth) {
        String value = detailsOf(auth).get("maxVotes");
        return value != null ? Integer.parseInt(value) : 0;
    }

    public static String anonymityLevel(Authentication auth) {
        return detailsOf(auth).get("anonymityLevel");
    }
}
