package com.demo.bankingsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends BaseEntity {
    @Column(name = "transaction_id", unique = true)
    private String transactionId;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;
    @Column(name = "initial_balance")
    private BigDecimal initialBalance;
    @Column(name = "account_balance")
    private BigDecimal accountBalance;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String message;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "initiator_id")
    private Customer customer;


    public Transaction(String transactionId,
                       TransactionType transactionType,
                       BigDecimal transactionAmount,
                       BigDecimal initialBalance,
                       BigDecimal accountBalance,
                       TransactionStatus status,
                       String message, Customer customer) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.initialBalance = initialBalance;
        this.accountBalance = accountBalance;
        this.status = status;
        this.message = message;
        this.customer = customer;
    }
}
