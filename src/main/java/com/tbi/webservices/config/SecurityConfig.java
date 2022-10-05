package com.tbi.webservices.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final HttpSecurity http;
    @Bean
    public SecurityFilterChain filterChain() throws Exception {
        return http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().build();
    }

}