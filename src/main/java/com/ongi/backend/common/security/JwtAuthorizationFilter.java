package com.ongi.backend.common.security;

import com.ongi.backend.domain.auth.entity.enums.Authority;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.ongi.backend.common.security.JwtTokenizer.BEARER_PREFIX;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        String accessToken = null;
        if(bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            accessToken = bearerToken.substring(7).trim();
        }

        if(accessToken != null) {
            jwtTokenizer.validateAccessToken(accessToken);

            Claims claims = jwtTokenizer.getClaimsFromAccessToken(accessToken);
            Long userId = Long.valueOf(claims.getSubject());
            Authority authority = Authority.valueOf(claims.get("role", String.class));

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority.name()));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}