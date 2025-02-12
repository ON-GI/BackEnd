package com.ongi.backend.domain.caregiver.repository;

import com.ongi.backend.domain.caregiver.entity.CaregiverWorkCondition;
import com.ongi.backend.domain.caregiver.entity.CaregiverWorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaregiverWorkTimeRepository extends JpaRepository<CaregiverWorkTime, Long> {
    void deleteByWorkCondition(CaregiverWorkCondition workCondition);
}
