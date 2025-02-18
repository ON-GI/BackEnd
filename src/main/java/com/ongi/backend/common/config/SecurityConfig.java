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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(
                jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/refresh").permitAll()
                .requestMatchers("/api/v1/caregiver/validate-id").permitAll()
                .requestMatchers("/api/v1/caregiver/signup").permitAll()
                .requestMatchers("/api/v1/center-staff/validate-id").permitAll()
                .requestMatchers("/api/v1/center-staff/signup").permitAll()
                .requestMatchers("/api/v1/center/search").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                .requestMatchers("/api/v1/caregiver/**").hasRole("CAREGIVER")
                .requestMatchers("/api/v1/senior/**").hasAnyRole("CENTER_MANAGER", "SOCIAL_WORKER")
                .requestMatchers("/api/v1/center/**").hasAnyRole("CENTER_MANAGER")
                .requestMatchers("/api/v1/center-staff/**").hasAnyRole("CENTER_MANAGER", "SOCIAL_WORKER")
                .requestMatchers("/api/v1/senior/**").hasAnyRole("CENTER_MANAGER", "SOCIAL_WORKER")
                .requestMatchers("/api/v1/matching/center/**").hasAnyRole("CENTER_MANAGER", "SOCIAL_WORKER")
                .anyRequest().authenticated()
        );

        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("*")); // ✅ 모든 도메인에서 접근 가능
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // ✅ 허용할 HTTP 메서드
        config.setAllowedHeaders(List.of("*")); // ✅ 모든 헤더 허용
        config.setAllowCredentials(true); // ✅ 인증 정보 포함 요청 허용 (JWT 포함)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
