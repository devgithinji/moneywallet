package com.demo.bankingsystem.dao.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerLoginResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
}
