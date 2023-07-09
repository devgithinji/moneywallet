package com.demo.bankingsystem.exception;

public class ApiException extends RuntimeException {
    private String message;

    public ApiException(String message) {
        super(message);
    }
}
