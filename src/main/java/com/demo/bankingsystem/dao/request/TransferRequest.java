package com.demo.bankingsystem.dao.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
@Data
public class TransferRequest {
    @Min(value = 50, message = "Amount should be at least 50")
    private BigDecimal amount;
    @NotBlank(message = "pin is required")
    private String pin;
    @NotBlank(message = "recipient account number is required")
    private String recipientAccountNo;

}
