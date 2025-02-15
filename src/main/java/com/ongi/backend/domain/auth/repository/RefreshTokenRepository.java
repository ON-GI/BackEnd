package com.ongi.backend.domain.auth.repository;

import com.ongi.backend.domain.auth.entity.RefreshToken;
import com.ongi.backend.domain.auth.entity.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByUserIdAndUserType(Long userId, UserType userType);
}
