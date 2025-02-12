package com.ongi.backend.controller;

import com.ongi.backend.dto.caregiver.CaregiverRequestDto;
import com.ongi.backend.service.CaregiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/caregiver")
public class CaregiverController {

    private final CaregiverService caregiverService;

//    @PostMapping("/user/signup")
//    public void registerCaregiver(@RequestBody CaregiverRequestDto caregiverRequestDto) {
//        caregiverService.registerCaregiver(caregiverRequestDto);
//    }
}
