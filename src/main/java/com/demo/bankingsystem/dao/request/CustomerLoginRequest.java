package com.demo.bankingsystem.dao.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerLoginRequest {
    @NotBlank(message = "id number is required")
    private String idNumber;
    @NotBlank(message = "pin is required")
    private String pin;
}
