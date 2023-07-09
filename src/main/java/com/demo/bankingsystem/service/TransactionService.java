package com.demo.bankingsystem.service;

import com.demo.bankingsystem.dao.request.DepositRequest;
import com.demo.bankingsystem.dao.request.TransactionSearchRequest;
import com.demo.bankingsystem.dao.request.TransferRequest;
import com.demo.bankingsystem.dao.request.WithdrawRequest;
import com.demo.bankingsystem.dao.response.CheckBalanceResponse;
import com.demo.bankingsystem.dao.response.MiniStatementResponse;
import com.demo.bankingsystem.dao.response.TransactionResponse;
import com.demo.bankingsystem.dao.response.TransactionSearchResponse;

public interface TransactionService {

    CheckBalanceResponse checkBalance();

    MiniStatementResponse getMinistatement();

    TransactionResponse depositFunds(DepositRequest depositRequest);

    TransactionResponse withdrawFunds(WithdrawRequest withdrawRequest);

    TransactionResponse transferFunds(TransferRequest transferRequest);

    TransactionSearchResponse searchTransaction(TransactionSearchRequest transactionSearchRequest);
}
