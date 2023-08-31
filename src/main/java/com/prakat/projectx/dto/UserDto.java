package com.prakat.projectx.dto;

import com.prakat.projectx.enums.Role;

import com.prakat.projectx.enums.PasswordStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;

@Data
public class UserDto {

    @NotNull
    private BigInteger id;

    @NotNull
    private String firstName;

    private String lastName;

    @NotNull
    private String email;

    private String password;

    private Boolean active;

    private Role role;
    @NotNull
    private String secretCode;

    private PasswordStatus passwordStatus;
    private Integer otpAttempts;


}
