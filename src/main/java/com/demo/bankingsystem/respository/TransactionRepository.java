package com.demo.bankingsystem.respository;

import com.demo.bankingsystem.model.Customer;
import com.demo.bankingsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionByTransactionId(String transactionId);

    List<Transaction> findFirst10TransactionByCustomerOrderByCreatedAtDesc(Customer customer);

}
