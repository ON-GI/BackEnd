package com.ongi.backend.common.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String errorMessage = "인증이 실패했습니다.";  // 기본 메시지

        if (authException.getCause() instanceof ExpiredJwtException) {
            errorMessage = "토큰이 만료되었습니다.";  // 토큰 만료 오류
        }

        response.getWriter().write("{\"message\":\"" + errorMessage + "\"}");
    }
}

