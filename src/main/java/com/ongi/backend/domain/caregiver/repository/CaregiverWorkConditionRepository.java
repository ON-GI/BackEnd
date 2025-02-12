package com.ongi.backend.domain.caregiver.repository;

import com.ongi.backend.domain.caregiver.entity.CaregiverWorkCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaregiverWorkConditionRepository extends JpaRepository<CaregiverWorkCondition, Long> {

    Optional<CaregiverWorkCondition> findByCaregiverId(Long caregiverId);
}
