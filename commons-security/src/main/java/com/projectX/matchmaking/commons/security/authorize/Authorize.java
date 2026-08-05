package com.projectX.matchmaking.commons.security.authorize;

import com.projectX.matchmaking.commons.token.Role;
import com.projectX.matchmaking.commons.token.TokenType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares the token this controller method requires. Enforced by {@link AuthorizationAspect}
 * before the method body runs: missing token / wrong role / wrong type throws
 * {@link UnauthorizedException} (401); a roomId claim that doesn't match {@code roomIdParam}
 * throws {@link ForbiddenException} (403).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorize {

    Role[] roles();

    TokenType type();

    /**
     * Name of the {@code @PathVariable} holding the roomId to check against the token's
     * {@code roomId} claim. Leave blank to skip room-scoping (e.g. for APP-type tokens).
     */
    String roomIdParam() default "";
}
