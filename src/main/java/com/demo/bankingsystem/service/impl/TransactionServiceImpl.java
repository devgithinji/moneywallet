package com.demo.bankingsystem.service.impl;

import com.demo.bankingsystem.dao.request.DepositRequest;
import com.demo.bankingsystem.dao.request.TransactionSearchRequest;
import com.demo.bankingsystem.dao.request.TransferRequest;
import com.demo.bankingsystem.dao.request.WithdrawRequest;
import com.demo.bankingsystem.dao.response.CheckBalanceResponse;
import com.demo.bankingsystem.dao.response.MiniStatementResponse;
import com.demo.bankingsystem.dao.response.TransactionResponse;
import com.demo.bankingsystem.dao.response.TransactionSearchResponse;
import com.demo.bankingsystem.exception.ResourceNotFoundException;
import com.demo.bankingsystem.model.*;
import com.demo.bankingsystem.respository.AccountRepository;
import com.demo.bankingsystem.respository.CustomerRepository;
import com.demo.bankingsystem.respository.TransactionRepository;
import com.demo.bankingsystem.service.TransactionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    public final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository,
                                  CustomerRepository customerRepository,
                                  TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public CheckBalanceResponse checkBalance() {
        Customer customer = getCustomer();

        return new CheckBalanceResponse(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAccount().getAccountNo(),
                customer.getAccount().getBalance(),
                LocalDateTime.now());
    }

    @Override
    public MiniStatementResponse getMinistatement() {
        Customer customer = getCustomer();

        List<TransactionResponse> transactionResponses = transactionRepository.findFirst10TransactionByCustomerOrderByCreatedAtDesc(customer).stream().map(transaction -> new TransactionResponse(
                transaction.getTransactionId(),
                customer.getAccount().getAccountNo(),
                transaction.getTransactionAmount().toString(),
                transaction.getInitialBalance().toString(),
                transaction.getAccountBalance().toString(),
                transaction.getTransactionType(),
                transaction.getMessage(),
                transaction.getCreatedAt()
        )).toList();

        return new MiniStatementResponse(
                customer.getAccount().getAccountNo(),
                transactionResponses,
                LocalDateTime.now()
        );
    }

    @Override
    public TransactionResponse depositFunds(DepositRequest depositRequest) {
        Customer customer = getCustomer();
        BigDecimal intialBalance = customer.getAccount().getBalance();
        customer.getAccount().deposit(depositRequest.getAmount());
        Customer updatedCustomer = customerRepository.save(customer);

        return getTransactionResponse(TransactionType.DEPOSIT,
                intialBalance,
                depositRequest.getAmount(),
                updatedCustomer,
                null
        );
    }


    @Override
    public TransactionResponse withdrawFunds(WithdrawRequest withdrawRequest) {
        Customer customer = getCustomer();
        BigDecimal intialBalance = customer.getAccount().getBalance();
        customer.getAccount().withdraw(withdrawRequest.getAmount());
        Customer updatedCustomer = customerRepository.save(customer);

        return getTransactionResponse(
                TransactionType.WITHDRAWAL,
                intialBalance,
                withdrawRequest.getAmount(),
                updatedCustomer,
                null
        );
    }

    @Override
    public TransactionResponse transferFunds(TransferRequest transferRequest) {
        Customer customer = getCustomer();
        BigDecimal intialBalance = customer.getAccount().getBalance();
        customer.getAccount().withdraw(transferRequest.getAmount());
        Customer updatedCustomer = customerRepository.save(customer);

        Account account = accountRepository.findAccountByAccountNo(transferRequest.getRecipientAccountNo()).orElseThrow(() ->
                new ResourceNotFoundException("account", "account number", transferRequest.getRecipientAccountNo()));

        account.deposit(transferRequest.getAmount());
        accountRepository.save(account);

        return getTransactionResponse(
                TransactionType.TRANSFER,
                intialBalance,
                transferRequest.getAmount(),
                updatedCustomer,
                transferRequest.getRecipientAccountNo()
        );
    }

    private TransactionResponse getTransactionResponse(TransactionType transactionType,
                                                       BigDecimal initialAmount,
                                                       BigDecimal transactionAmount,
                                                       Customer updatedCustomer,
                                                       String recipientAccountBNumber) {


        String message = getMessage(transactionType, transactionAmount, updatedCustomer, recipientAccountBNumber);

        Transaction savedTransaction = getSavedTransaction(transactionType, initialAmount, transactionAmount, updatedCustomer, message);

        return new TransactionResponse(
                savedTransaction.getTransactionId(),
                updatedCustomer.getAccount().getAccountNo(),
                transactionAmount.toString(),
                savedTransaction.getInitialBalance().toString(),
                updatedCustomer.getAccount().getBalance().toString(),
                transactionType,
                message,
                LocalDateTime.now()
        );
    }

    private Transaction getSavedTransaction(TransactionType transactionType, BigDecimal initialAmount, BigDecimal transactionAmount, Customer updatedCustomer, String message) {
        Transaction transaction = new Transaction(
                generateTransactionId(),
                transactionType,
                transactionAmount,
                initialAmount,
                updatedCustomer.getAccount().getBalance(),
                TransactionStatus.COMPLETED,
                message,
                updatedCustomer
        );

        return transactionRepository.save(transaction);
    }

    private String getMessage(TransactionType transactionType, BigDecimal transactionAmount, Customer updatedCustomer, String recipientAccountBNumber) {
        return "%s %s ksh to account number: %s, current Balance: %s, at %s".formatted(
                transactionType.name(),
                transactionAmount,
                transactionType == TransactionType.TRANSFER ? recipientAccountBNumber : updatedCustomer.getAccount().getAccountNo(),
                updatedCustomer.getAccount().getBalance(),
                formattedDateTime()
        );
    }


    @Override
    public TransactionSearchResponse searchTransaction(TransactionSearchRequest transactionSearchRequest) {
        Transaction transaction = transactionRepository.findTransactionByTransactionId(transactionSearchRequest.getAccountNumber()).orElseThrow(() ->
                new ResourceNotFoundException("transaction", "transaction id", transactionSearchRequest.getAccountNumber()));

        return new TransactionSearchResponse(
                transaction.getTransactionId(),
                transaction.getTransactionType(),
                transaction.getTransactionAmount(),
                transaction.getStatus(),
                transaction.getMessage(),
                transaction.getCreatedAt()
        );

    }

    private Customer getCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return (Customer) authentication.getPrincipal();
        }

        throw new AccessDeniedException("Authentication error");
    }

    public String generateTransactionId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String formattedDateTime() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm:ss");

        return now.format(formatter);
    }
}
