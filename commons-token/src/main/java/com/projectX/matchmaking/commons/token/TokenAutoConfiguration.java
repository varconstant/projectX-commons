package com.projectX.matchmaking.commons.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class TokenAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TokenProvider tokenProvider(@Value("${app.jwt.secret}") String jwtSecret){
        return new TokenProvider(jwtSecret);
    }
}
