package com.demo.bankingsystem.controller;

import com.demo.bankingsystem.dao.request.DepositRequest;
import com.demo.bankingsystem.dao.request.TransactionSearchRequest;
import com.demo.bankingsystem.dao.request.TransferRequest;
import com.demo.bankingsystem.dao.request.WithdrawRequest;
import com.demo.bankingsystem.dao.response.CheckBalanceResponse;
import com.demo.bankingsystem.dao.response.MiniStatementResponse;
import com.demo.bankingsystem.dao.response.TransactionResponse;
import com.demo.bankingsystem.dao.response.TransactionSearchResponse;
import com.demo.bankingsystem.service.TransactionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class AccountController {

    private TransactionService transactionService;

    public AccountController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/check-balance")
    public CheckBalanceResponse checkBalance() {
        return transactionService.checkBalance();

    }

    @GetMapping("/mini-statement")
    public MiniStatementResponse getMinistatement() {
        return transactionService.getMinistatement();
    }

    @PostMapping("/deposit")
    public TransactionResponse depositFunds(@Valid @RequestBody DepositRequest depositRequest) {
        return transactionService.depositFunds(depositRequest);
    }

    @PostMapping("/withdraw")
    public TransactionResponse withdrawFunds(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        return transactionService.withdrawFunds(withdrawRequest);
    }

    @PostMapping("/transfer")
    public TransactionResponse transferFunds(@Valid @RequestBody TransferRequest transferRequest) {
        return transactionService.transferFunds(transferRequest);
    }

    @PostMapping("/search")
    @Validated
    public TransactionSearchResponse searchTransaction(@Valid @RequestBody TransactionSearchRequest transactionSearchRequest) {
        return transactionService.searchTransaction(transactionSearchRequest);
    }
}
