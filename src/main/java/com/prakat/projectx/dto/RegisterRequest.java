package com.prakat.projectx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The {@code RegisterRequest} class represents a request to register a new user.
 * It contains the necessary information required for user registration.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private static final Logger logger= LoggerFactory.getLogger(RegisterRequest.class);
    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
