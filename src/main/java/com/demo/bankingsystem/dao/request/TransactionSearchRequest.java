package com.demo.bankingsystem.dao.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TransactionSearchRequest {
    @NotBlank(message = "account number is required")
    private String accountNumber;
}
