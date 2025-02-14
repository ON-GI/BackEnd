package com.ongi.backend.domain.caregiver.repository;

import com.ongi.backend.domain.caregiver.entity.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long>, CaregiverRepositoryCustom {
    Optional<Caregiver> findById(Long caregiverId);

    boolean existsByLoginId(String loginId);
}
