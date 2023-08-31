package com.prakat.projectx.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String jwtAccessToken);

    <T> T extractClaim(String jwtAccessToken, Function<Claims, T> claimsResolver);

    String generateToken(String userEmail, String secretCode);

    String generateToken(Map<String, Object> extractClaims, UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractSecretCode(String jwtAccessToken);

    Boolean isTokenValid(String jwtToken, UserDetails userDetails);

    boolean isSecretCodeValid(String secretCode, UserDetails userDetails);

    Boolean isRefreshTokenValid(String refreshToken, UserDetails userDetails);

    boolean isTokenExpired(String jwtAccessToken);

    Date extractExpiration(String jwtAccessToken);

    Claims extractAllClaims(String jwtAccessToken);

    UserDetails getUserDetailsFromRefreshToken(String refreshToken);
}
