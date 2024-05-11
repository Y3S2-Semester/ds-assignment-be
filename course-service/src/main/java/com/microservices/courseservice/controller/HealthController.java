package com.microservices.courseservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/health")
@AllArgsConstructor
public class HealthController {

    @GetMapping
    public String healthCheck() {
        return "course-service is up and running!!";
    }
}
