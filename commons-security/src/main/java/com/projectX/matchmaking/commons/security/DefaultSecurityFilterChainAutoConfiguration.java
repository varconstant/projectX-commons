package com.projectX.matchmaking.commons.security;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/** The standard security rules shared by every JWT-backed service: attach the JWT filter, permit
 * /api/v1/** and /actuator/**, authenticate everything else. A service with different routing
 * needs (or that doesn't use commons-security's JWT filter at all) just declares its own
 * SecurityFilterChain bean — @ConditionalOnMissingBean steps aside for it. */
@AutoConfiguration
@EnableWebSecurity
public class DefaultSecurityFilterChainAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtSecurityConfigurer jwtConfigurer) {
        jwtConfigurer.configure(http);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
        );

        return http.build();
    }
}
