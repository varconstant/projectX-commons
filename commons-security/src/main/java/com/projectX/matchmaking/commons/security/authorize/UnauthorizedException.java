package com.projectX.matchmaking.commons.security.authorize;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
