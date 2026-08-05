package com.projectX.matchmaking.commons.security.authorize;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
