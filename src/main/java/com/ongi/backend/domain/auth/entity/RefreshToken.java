package com.ongi.backend.domain.auth.entity;

import com.ongi.backend.common.enums.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
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
