package com.ongi.backend.domain.auth.entity;

import com.ongi.backend.domain.auth.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private UserType userType;

    @Column(nullable = false)
    private String refreshToken;

    public static RefreshToken from(Long userId, UserType userType, String refreshToken) {
        return RefreshToken.builder()
                .userId(userId)
                .userType(userType)
                .refreshToken(refreshToken)
                .build();
    }
}
