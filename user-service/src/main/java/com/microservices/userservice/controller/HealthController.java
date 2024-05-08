package com.microservices.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
@AllArgsConstructor
public class HealthController {

    @GetMapping
    public String healthCheck() {
        return "API is up and running!";
    }
}
