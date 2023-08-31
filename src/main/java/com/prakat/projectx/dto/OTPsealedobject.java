package com.prakat.projectx.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class OTPsealedobject implements Serializable {

    private String userEmail;
    private int otp;
    private Date timestamp;

}
