package com.ongi.backend.domain.center.service;

import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CenterService {

    private final CenterRepository centerRepository;

    @Transactional
    public Center findCenter(Long centerId) {
        return centerRepository.findById(centerId).get();
    }
}
