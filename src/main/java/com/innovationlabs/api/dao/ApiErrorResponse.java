package com.innovationlabs.api.dao;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private int code;
    private String message;

    public ApiErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}