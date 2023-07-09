package com.demo.bankingsystem.respository;

import com.demo.bankingsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByIdNumber(String idNumber);

    Optional<Customer> findCustomerByEmail(String email);

    Optional<Customer> findCustomerByIdNumberAndEmail(String idNumber, String email);
}
