package com.demo.bankingsystem.dao.request;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
@Data
public class WithdrawRequest {
    @Min(value = 1, message = "amount should be more than 0")
    private BigDecimal amount;
    @NotBlank(message = "pin is required")
    private String pin;
}
