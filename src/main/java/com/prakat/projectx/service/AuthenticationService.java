package com.prakat.projectx.service;

import com.prakat.projectx.dto.AuthenticationRequest;
import com.prakat.projectx.dto.AuthenticationResponse;
import com.prakat.projectx.dto.RegisterRequest;
import com.prakat.projectx.entity.User;
import jakarta.servlet.http.Cookie;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    Cookie createRefreshTokenCookie(String refreshToken);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse generateAuthenticationResponse (User user);

    AuthenticationResponse refreshToken(String refreshToken);
}
