package com.projectX.matchmaking.commons.security;

import com.projectX.matchmaking.commons.filter.JwtAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfigurer {

    private final JwtAuthenticationFilter jwtFilter;

    public JwtSecurityConfigurer(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    public void configure(HttpSecurity http){
        http.csrf(AbstractHttpConfigurer::disable).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
