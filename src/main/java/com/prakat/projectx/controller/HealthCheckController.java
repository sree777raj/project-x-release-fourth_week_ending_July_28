package com.prakat.projectx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        boolean isHealthy = true; // Modify this based on your actual health check logic

        if (isHealthy) {
            return ResponseEntity.ok("Application is healthy!");
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Application is unhealthy!");
        }
    }
}
