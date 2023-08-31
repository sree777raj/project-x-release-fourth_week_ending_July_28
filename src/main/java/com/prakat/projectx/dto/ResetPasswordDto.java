package com.prakat.projectx.dto;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.SealedObject;

@Getter
@Setter
public class ResetPasswordDto {


    private String otpsealedobject;
    private String newPassword;
    private int otp;



}
