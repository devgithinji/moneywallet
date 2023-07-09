package com.demo.bankingsystem.service.impl;

import com.demo.bankingsystem.auth.JwtTokenProvider;
import com.demo.bankingsystem.dao.request.CustomerLoginRequest;
import com.demo.bankingsystem.dao.request.CustomerRegisterRequest;
import com.demo.bankingsystem.dao.response.CustomerLoginResponse;
import com.demo.bankingsystem.dao.response.CustomerRegisterResponse;
import com.demo.bankingsystem.exception.ApiException;
import com.demo.bankingsystem.model.Account;
import com.demo.bankingsystem.model.Customer;
import com.demo.bankingsystem.respository.AccountRepository;
import com.demo.bankingsystem.respository.CustomerRepository;
import com.demo.bankingsystem.service.CustomerService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String ACCOUNT_NUMBER_PREFIX = "ACC";

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               AccountRepository accountRepository,
                               PasswordEncoder passwordEncoder,
                               JwtTokenProvider jwtTokenProvider,
                               AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.random = new Random();
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public CustomerRegisterResponse registerCustomer(CustomerRegisterRequest customerRegisterRequest) {
        if (customerRepository.findCustomerByEmail(customerRegisterRequest.getEmail()).isPresent())
            throw new ApiException("email is taken");

        if (customerRepository.findCustomerByIdNumber(customerRegisterRequest.getIdNumber()).isPresent())
            throw new ApiException("id number is taken");

        String randomPin = String.valueOf(generatePin());
        String accountNumber = generateAccountNumber();

        //create customer
        Customer customer = new Customer(
                customerRegisterRequest.getFirstName(),
                customerRegisterRequest.getLastName(),
                customerRegisterRequest.getEmail(),
                passwordEncoder.encode(randomPin),
                customerRegisterRequest.getIdNumber());

        //create account
        Account account = new Account(accountNumber, BigDecimal.ZERO);

        customer.setAccount(account);
        account.setCustomer(customer);

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerRegisterResponse(
                randomPin,
                savedCustomer.getAccount().getAccountNo(),
                savedCustomer.getAccount().getBalance());
    }

    private String generateAccountNumber() {
        String accountNumber;
        Set<String> generatedAccountNumbers = accountRepository.findAll().stream().map(Account::getAccountNo).collect(Collectors.toSet());

        do {
            int uniqueId = random.nextInt(900000) + 100000; // Generate a 6-digit unique identifier
            accountNumber = ACCOUNT_NUMBER_PREFIX + "-" + uniqueId;
        } while (generatedAccountNumbers.contains(accountNumber));

        return accountNumber;
    }


    private int generatePin() {
        return 1000 + random.nextInt(9000);
    }

    @Override
    public CustomerLoginResponse loginCustomer(CustomerLoginRequest customerLoginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerLoginRequest.getIdNumber(), customerLoginRequest.getPin()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String token = "Bearer "+jwtTokenProvider.generateToken(authentication);
            return new CustomerLoginResponse(customer.getFirstName(), customer.getLastName(), customer.getEmail(), token);
        } catch (Exception e) {
            throw new ApiException("invalid credentials");
        }
    }

}
