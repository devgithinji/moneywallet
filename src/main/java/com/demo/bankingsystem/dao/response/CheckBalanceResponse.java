package com.demo.bankingsystem.dao.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CheckBalanceResponse {
    private String firstName;
    private String lastName;
    private String accountNumber;
    private BigDecimal balance;
    @JsonFormat(pattern = "dd MM yyyy HH:mm:ss")
    private LocalDateTime localDateTime;
}
