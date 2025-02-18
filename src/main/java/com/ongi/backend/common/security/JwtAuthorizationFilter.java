package com.ongi.backend.common.security;

import com.ongi.backend.common.enums.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        String accessToken = null;
        if(bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            accessToken = bearerToken.substring(7).trim();
        }

        if(accessToken != null) {
            try {
                jwtTokenizer.validateAccessToken(accessToken);

                Claims claims = jwtTokenizer.getClaimsFromAccessToken(accessToken);
                Long userId = Long.valueOf(claims.getSubject());
                Authority authority = Authority.valueOf(claims.get("role", String.class));

                Long centerId = claims.get("centerId", Long.class);

                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority.name()));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, centerId, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                request.setAttribute("errorMessage", "토큰이 만료되었습니다.");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "인증이 실패했습니다.");
            }
        }

        filterChain.doFilter(request, response);
    }
}