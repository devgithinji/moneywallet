package com.demo.bankingsystem.dao.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CustomerRegisterResponse {
    private String pin;
    private String accountNumber;
    private BigDecimal balance;
}
