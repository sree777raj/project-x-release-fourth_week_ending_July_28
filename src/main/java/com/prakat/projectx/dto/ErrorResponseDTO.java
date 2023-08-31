package com.prakat.projectx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDTO {

    private int errorCode;
    private String errorMessage;
    private String webRequest;
    @JsonIgnore
    private Optional<String> debugMessage;
    private String timeStamp;

    public ErrorResponseDTO(int errorCode, String errorMessage, String webRequest,Optional<String> debugMessage) {
        this(errorCode,errorMessage,webRequest);
        this.debugMessage=debugMessage;

    }
    public ErrorResponseDTO(int errorCode, String errorMessage, String webRequest) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.webRequest = webRequest;
        this.timeStamp = LocalDateTime.now().toString();
    }
}
