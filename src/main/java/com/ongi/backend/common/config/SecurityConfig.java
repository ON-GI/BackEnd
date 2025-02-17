package com.ongi.backend.common.config;

import com.ongi.backend.common.security.CustomAccessDeniedHandler;
import com.ongi.backend.common.security.CustomAuthenticationEntryPoint;
import com.ongi.backend.common.security.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(
                jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/v1/auth/caregiver/login").permitAll()
                .requestMatchers("/api/v1/caregiver/validate-id").permitAll()
                .requestMatchers("/api/v1/caregiver/signup").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/api/v1/senior/**").permitAll()
                .requestMatchers("/api/v1/center/").permitAll()
                .requestMatchers("/api/v1/center/search").permitAll()
                .requestMatchers("/api/v1/caregiver/**").hasRole("CAREGIVER")
                .requestMatchers("/api/v1/center/**").hasRole("CENTER")
                .anyRequest().authenticated()
        );

        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );

        return http.build();
    }
}
