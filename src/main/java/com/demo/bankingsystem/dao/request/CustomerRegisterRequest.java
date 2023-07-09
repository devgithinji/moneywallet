package com.demo.bankingsystem.dao.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CustomerRegisterRequest {
    @NotBlank(message = "first name is required")
    private String firstName;
    @NotBlank(message = "last name is required")
    private String lastName;
    @NotBlank(message = "email is required")
    @Email(message = "must be a valid email")
    private String email;
    @NotBlank(message = "id number is required")
    private String idNumber;
}
