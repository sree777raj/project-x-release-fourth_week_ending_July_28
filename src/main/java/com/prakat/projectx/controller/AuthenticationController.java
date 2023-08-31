package com.prakat.projectx.controller;


import com.prakat.projectx.dto.AuthenticationRequest;
import com.prakat.projectx.dto.AuthenticationResponse;
import com.prakat.projectx.serviceImpl.AuthenticationServiceImpl;
import com.prakat.projectx.dto.RegisterRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private static final Logger logger= LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationServiceImpl service;
    @PostMapping("/register")
    public ResponseEntity<String>register(@RequestBody RegisterRequest request){
       service.register(request);
        return ResponseEntity.ok("User Registration successful");
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        AuthenticationResponse authResponse = service.authenticate(request);

        // Create and add the refreshToken cookie to the response
        Cookie refreshTokenCookie = service.createRefreshTokenCookie(authResponse.getRefreshToken());
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok(authResponse);
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null) {
            // The refresh token is missing, so the user needs to log in again
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Call the AuthenticationServiceImpl's method to refresh the access token using the refresh token
        AuthenticationResponse authResponse = service.refreshToken(refreshToken);

        if (authResponse != null) {
            // Create and add the new refreshToken cookie to the response
            Cookie newRefreshTokenCookie = service.createRefreshTokenCookie(authResponse.getRefreshToken());
            response.addCookie(newRefreshTokenCookie);

            return ResponseEntity.ok(authResponse);
        } else {
            // The provided refresh token is invalid or expired, so the user needs to log in again
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
