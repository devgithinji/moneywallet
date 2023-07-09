package com.demo.bankingsystem.dao.response;

import com.demo.bankingsystem.model.TransactionStatus;
import com.demo.bankingsystem.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionSearchResponse {
    private String transactionId;
    private TransactionType transactionType;
    private BigDecimal transactionAmount;
    private TransactionStatus status;
    private String message;
    private LocalDateTime timestamp;
}
