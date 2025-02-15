package com.ongi.backend.common.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenizer {

    private static String BEARER_PREFIX = "Bearer";
    public static long REFRESH_TOKEN_EXPIRATION;

    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    private final Key accessKey;
    private final Key refreshKey;

    public JwtTokenizer(
            @Value("${jwt.accessToken.expiration}") long accessTokenExpiration,
            @Value("${jwt.refreshToken.expiration}") long refreshTokenExpiration,
            @Value("${jwt.accessToken.secret}") String accessSecret,
            @Value("${jwt.refreshToken.secret}") String refreshSecret
    ) {
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;

        byte[] bytes = Base64.getDecoder().decode(accessSecret);
        this.accessKey = Keys.hmacShaKeyFor(bytes);

        bytes = Base64.getDecoder().decode(refreshSecret);
        this.refreshKey = Keys.hmacShaKeyFor(bytes);
    }

    @PostConstruct
    public void init() {
        REFRESH_TOKEN_EXPIRATION = refreshTokenExpiration;
    }

    public String createAccessToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiration))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpiration))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
