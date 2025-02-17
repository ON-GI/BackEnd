package com.ongi.backend.domain.centerstaff.repository;

import com.ongi.backend.domain.centerstaff.entity.CenterStaff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CenterStaffRepository extends JpaRepository<CenterStaff, Long> {
    Optional<CenterStaff> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
