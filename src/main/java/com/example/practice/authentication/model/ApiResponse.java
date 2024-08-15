package com.example.practice.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse implements Serializable {

    private LocalDateTime timeStamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;
    private String errorCode;
    private String errorType;
    private Object data;

    public ApiResponse(int statusCode, String error, String message, String path, String errorCode, String errorType, LocalDateTime timeStamp) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
        this.errorType = errorType;
        this.timeStamp = timeStamp;
    }
}
