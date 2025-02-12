package com.ongi.backend.domain.caregiver.repository;

import com.ongi.backend.domain.caregiver.entity.CaregiverWorkCondition;
import com.ongi.backend.domain.caregiver.entity.CaregiverWorkRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaregiverWorkRegionRepository extends JpaRepository<CaregiverWorkRegion, Long> {
    void deleteByWorkCondition(CaregiverWorkCondition workCondition);
}
