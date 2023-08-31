package com.prakat.projectx.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;

@Data
public class RoleDto {

    @NotNull
    private BigInteger id;

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private Boolean active;
}
