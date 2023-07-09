package com.demo.bankingsystem.respository;

import com.demo.bankingsystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByAccountNo(String accountNumber);
}
