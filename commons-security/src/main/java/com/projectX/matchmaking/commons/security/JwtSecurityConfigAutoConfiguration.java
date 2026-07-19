package com.projectX.matchmaking.commons.security;

import com.projectX.matchmaking.commons.filter.JwtAuthenticationFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class JwtSecurityConfigAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public JwtSecurityConfigurer jwtSecurityConfigurer(JwtAuthenticationFilter jwtFilter) {
        return new JwtSecurityConfigurer(jwtFilter);
    }
}
