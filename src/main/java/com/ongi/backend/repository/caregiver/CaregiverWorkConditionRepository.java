package com.ongi.backend.repository.caregiver;

import com.ongi.backend.entity.caregiver.CaregiverWorkCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaregiverWorkConditionRepository extends JpaRepository<CaregiverWorkCondition, Long> {

    Optional<CaregiverWorkCondition> findByCaregiverId(Long caregiverId);
}
