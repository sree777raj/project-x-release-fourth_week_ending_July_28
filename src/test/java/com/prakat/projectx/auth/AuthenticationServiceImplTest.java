package com.prakat.projectx.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.prakat.projectx.dto.AuthenticationRequest;
import com.prakat.projectx.dto.AuthenticationResponse;
import com.prakat.projectx.dto.RegisterRequest;
import com.prakat.projectx.serviceImpl.AuthenticationServiceImpl;
import com.prakat.projectx.serviceImpl.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.prakat.projectx.entity.User;
import com.prakat.projectx.exception.CustomException;
import com.prakat.projectx.repo.UserRepository;


import java.util.Optional;

public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImpl jwtServiceImpl;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testRegister_Success() {
        // Mock the dependencies
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        // Create a RegisterRequest
        RegisterRequest request = new RegisterRequest("Satish", "Shinde", "satish@example.com", "password");

        // Invoke the registration process
        AuthenticationResponse response = authenticationServiceImpl.register(request);

        // Assert the response
        assertNotNull(response); // Assuming response should not be null

        // Verify the interactions with mocked dependencies
        verify(userRepository).existsByEmail("satish@example.com");
        verify(passwordEncoder).encode("password");
        verify(jwtServiceImpl, never()).generateToken(anyString(), anyString()); // Ensure token generation is not called
    }




    @Test
    public void testRegister_EmailAlreadyExists() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        RegisterRequest request = new RegisterRequest("Satish", "Shinde", "satish@example.com", "password");

        assertThrows(CustomException.class, () -> authenticationServiceImpl.register(request));
    }

    @Test
    public void testAuthenticate_Success() {
        AuthenticationRequest request = new AuthenticationRequest("satish@example.com", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        User user = new User();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        when(jwtServiceImpl.generateToken((String) any(), any())).thenReturn("dummyJwtToken");

        assertDoesNotThrow(() -> authenticationServiceImpl.authenticate(request));
    }

    @Test
    public void testAuthenticate_InvalidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest("satish@example.com", "invalidPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException());

        User user = new User();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> authenticationServiceImpl.authenticate(request));
    }
}
