package com.demo.bankingsystem.dao.request;


import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
@Data
public class DepositRequest {
    @Min(value = 100, message = "Amount must be at least 100")
    private BigDecimal amount;

}
