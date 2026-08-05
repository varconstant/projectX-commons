package com.projectX.matchmaking.commons.security.authorize;

public class AuthorizationErrorResponse {
    private final String error;
    private final String message;

    public AuthorizationErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
