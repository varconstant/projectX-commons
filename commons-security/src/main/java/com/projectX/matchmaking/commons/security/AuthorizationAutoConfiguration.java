package com.projectX.matchmaking.commons.security;

import com.projectX.matchmaking.commons.security.authorize.AuthorizationAspect;
import com.projectX.matchmaking.commons.security.authorize.AuthorizationExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class AuthorizationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuthorizationAspect authorizationAspect() {
        return new AuthorizationAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthorizationExceptionHandler authorizationExceptionHandler() {
        return new AuthorizationExceptionHandler();
    }
}
