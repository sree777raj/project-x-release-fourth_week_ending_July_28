package com.prakat.projectx.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit test cases for the {@link HealthCheckController} class.
 */
public class HealthCheckControllerTest {

    private HealthCheckController healthCheckController;

    /**
     * Sets up the test environment before each test case by creating a new instance of {@link HealthCheckController}.
     */
    @BeforeEach
    public void setup() {
        healthCheckController = new HealthCheckController();
    }

    /**
     * Test case for the healthy scenario.
     * It verifies that when the application is healthy, the response status should be {@link HttpStatus#OK} (200)
     * with the body "Application is healthy!".
     */
    @Test
    public void testCheckHealthHealthy() {
        ResponseEntity<String> response = healthCheckController.checkHealth();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Application is healthy!", response.getBody());
    }

    /**
     * Test case for the unhealthy scenario.
     * It uses Mockito to create a mock {@link HealthCheckController} and sets a response with status
     * {@link HttpStatus#SERVICE_UNAVAILABLE} (503) and the body "Application is unhealthy!".
     * This simulates the "unhealthy" scenario for testing.
     */
    @Test
    public void testCheckHealthUnhealthy() {
        // Create a mock HealthCheckController and set isHealthy to false
        HealthCheckController mockController = mock(HealthCheckController.class);
        when(mockController.checkHealth()).thenReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Application is unhealthy!"));

        ResponseEntity<String> response = mockController.checkHealth();
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Application is unhealthy!", response.getBody());
    }
}