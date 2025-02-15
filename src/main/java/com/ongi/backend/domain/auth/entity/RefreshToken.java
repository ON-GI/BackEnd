package com.ongi.backend.domain.auth.entity;

import com.ongi.backend.domain.auth.entity.enums.Authority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Column(nullable = false)
    private String refreshToken;

    public static RefreshToken from(Long userId, Authority userType, String refreshToken) {
        return RefreshToken.builder()
                .userId(userId)
                .authority(userType)
                .refreshToken(refreshToken)
                .build();
    }
}
