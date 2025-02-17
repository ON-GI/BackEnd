package com.ongi.backend.domain.center.repository;

import com.ongi.backend.domain.center.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Long> {
    List<Center> findTop10ByNameStartingWith(String name);
}
