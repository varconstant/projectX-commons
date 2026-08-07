package com.projectX.matchmaking.commons.security.authorize;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

@Aspect
public class AuthorizationAspect {

    @Before("@annotation(authorize)")
    public void enforce(Authorize authorize) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new UnauthorizedException("Missing or invalid token");
        }

        Map<String, String> details = AuthenticatedRequest.detailsOf(auth);
        String type = details.get("type");
        String role = details.get("role");

        if (!authorize.type().name().equals(type)) {
            throw new UnauthorizedException("Token type mismatch: expected " + authorize.type());
        }
        if (Arrays.stream(authorize.roles()).noneMatch(r -> r.name().equals(role))) {
            throw new UnauthorizedException("Role not permitted: " + role);
        }

        if (StringUtils.hasText(authorize.roomIdParam())) {
            String tokenRoomId = details.get("roomId");
            String pathRoomId = pathVariable(authorize.roomIdParam());
            if (tokenRoomId == null || !tokenRoomId.equals(pathRoomId)) {
                throw new ForbiddenException("Token is not scoped to this room");
            }
        }
    }

    private String pathVariable(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object attr = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (!(attr instanceof Map)) {
            return null;
        }
        Object value = ((Map<?, ?>) attr).get(name);
        return value != null ? value.toString() : null;
    }
}
