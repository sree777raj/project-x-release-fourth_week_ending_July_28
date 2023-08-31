package com.prakat.projectx.serviceImpl;

import com.prakat.projectx.dto.AuthenticationRequest;
import com.prakat.projectx.dto.AuthenticationResponse;
import com.prakat.projectx.dto.RegisterRequest;
import com.prakat.projectx.entity.User;
import com.prakat.projectx.enums.PasswordStatus;
import com.prakat.projectx.enums.Role;
import com.prakat.projectx.exception.CustomException;
import com.prakat.projectx.repo.UserRepository;

import com.prakat.projectx.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The {@code AuthenticationServiceImpl} class provides authentication-related services.
 * It handles user registration, authentication, and token management.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger= LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user with the provided information and returns an AuthenticationResponse containing JWT tokens.
     *
     * @param request The RegisterRequest object containing user registration details.
     * @return An AuthenticationResponse object containing JWT access and refresh tokens.
     */
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        // Create a new user object and save it to the repository
        if (repository.existsByEmail(request.getEmail())){
        throw new  CustomException("Email already exist try with different Email",400);
}
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .secretCode(UserServiceImpl.generateSecretCode())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .otpAttempts(0)
                .passwordStatus(PasswordStatus.NEW)
                .build();
        repository.save(user);

        return AuthenticationResponse.builder()
                .build();
    }

    /**
     * Creates a cookie containing the provided refresh token with appropriate settings.
     *
     * @param refreshToken The refresh token to be included in the cookie.
     * @return A Cookie object containing the refresh token.
     */
    @Override
    public Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // Set the max age to 7 days.
        refreshTokenCookie.setHttpOnly(true); // Make the cookie accessible only through HTTP requests, not JavaScript
        refreshTokenCookie.setPath("/"); // Set the cookie path to the root path so that it applies to all URLs
        return refreshTokenCookie;
    }

    /**
     * Authenticates a user with the provided credentials and generates JWT tokens upon successful authentication.
     *
     * @param request The AuthenticationRequest object containing user login credentials.
     * @return An AuthenticationResponse object containing JWT access and refresh tokens upon successful authentication.
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user using the provided credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // Find the authenticated user from the repository
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        // Generate JWT tokens for the authenticated user
        return generateAuthenticationResponse(user);
    }

    /**
     * Generates an AuthenticationResponse containing JWT access and refresh tokens for the provided user.
     *
     * @param user The User object for whom to generate the tokens.
     * @return An AuthenticationResponse object containing JWT access and refresh tokens.
     */
    @Override
    public AuthenticationResponse generateAuthenticationResponse (User user){
        var jwtToken= jwtServiceImpl.generateToken(user.getEmail(), user.getSecretCode());
        var refreshToken = jwtServiceImpl.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .jwtAccessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param refreshToken The refresh token to be used for obtaining a new access token.
     * @return An AuthenticationResponse object containing the new JWT access and refresh tokens, or null if refresh token is invalid.
     */
    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {
        // Validate the refresh token and get the user details using the JwtServiceImpl
        UserDetails userDetails = jwtServiceImpl.getUserDetailsFromRefreshToken(refreshToken);

        if ((userDetails != null) &&
                (jwtServiceImpl.isTokenValid(refreshToken, userDetails))) {
            // Generate new JWT tokens for the user and return the response
            return generateAuthenticationResponse((User) userDetails);
        }

        return null; // Invalid refresh token
    }
}
