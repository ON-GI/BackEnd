package com.ongi.backend.repository.caregiver;

import com.ongi.backend.entity.caregiver.CaregiverLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaregiverLicenseRepository extends JpaRepository<CaregiverLicense, Long> {
}
