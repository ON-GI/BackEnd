package com.ongi.backend.domain.auth.repository;

import com.ongi.backend.domain.auth.entity.RefreshToken;
import com.ongi.backend.domain.auth.entity.enums.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByUserIdAndAuthority(Long userId, Authority authority);

    Optional<RefreshToken> findByUserIdAndAuthority(Long userId, Authority authority);
}
