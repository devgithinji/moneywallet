package com.demo.bankingsystem.dao.response;

import com.demo.bankingsystem.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private String accountNumber;
    private String transactionAmount;
    private String initialBalance;
    private String currentBalance;
    private TransactionType transactionType;
    private String message;
    @JsonFormat(pattern = "dd MM yyyy HH:mm:ss")
    private LocalDateTime timestamp;
}
