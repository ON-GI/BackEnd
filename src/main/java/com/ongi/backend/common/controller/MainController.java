package com.ongi.backend.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "OK";
    }
}
