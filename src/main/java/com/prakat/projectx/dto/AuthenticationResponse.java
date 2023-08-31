package com.prakat.projectx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code AuthenticationResponse} class represents the response object for user authentication.
 * It contains the JWT access token and a refresh token (which is ignored during serialization).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationResponse.class);

    /**
     * The JWT access token generated after successful user authentication.
     */
    private String jwtAccessToken;

    /**
     * The refresh token generated after successful user authentication.
     * This token is meant for obtaining new access tokens without requiring the user to log in again.
     * It is ignored during serialization to avoid exposing sensitive information in the response.
     *
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    private String refreshToken;
}
