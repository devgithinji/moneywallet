package com.demo.bankingsystem.model;


import com.demo.bankingsystem.exception.ApiException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity {
    @Column(name = "account_no", unique = true)
    private String accountNo;
    private BigDecimal balance;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Account(String accountNo, BigDecimal balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }


    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }


    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) throw new ApiException("You have insufficient funds in your account");
        this.balance = this.balance.subtract(amount);
    }


}
