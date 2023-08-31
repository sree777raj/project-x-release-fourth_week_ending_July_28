package com.prakat.projectx.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Setter
@Getter
public class EmailDTO {

    private String to;
    private String subject;
    private String template;
    private Map<String, Object> emailData;

    // Generate Getters and Setters...
}
