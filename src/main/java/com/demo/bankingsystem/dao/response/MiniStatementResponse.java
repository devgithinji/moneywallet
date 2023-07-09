package com.demo.bankingsystem.dao.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MiniStatementResponse {
    private String accountNumber;
    private List<TransactionResponse> transactionResponses;
    @JsonFormat(pattern = "dd MM yyyy HH:mm:ss")
    private LocalDateTime localDateTime;
}
