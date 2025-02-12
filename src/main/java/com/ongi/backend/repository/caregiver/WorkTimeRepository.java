package com.ongi.backend.repository.caregiver;

import com.ongi.backend.entity.caregiver.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
}
