package com.microservices.notification.service.controller;

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
        return "notification-service is up and running!!";
    }
}
