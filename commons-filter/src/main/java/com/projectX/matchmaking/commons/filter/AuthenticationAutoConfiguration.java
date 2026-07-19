package com.projectX.matchmaking.commons.filter;

import com.projectX.matchmaking.commons.token.TokenProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class AuthenticationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationFilter jwtAuthenticationFilter(TokenProvider tokenProvider) {
        return new JwtAuthenticationFilter(tokenProvider);
    }
}