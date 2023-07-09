package com.demo.bankingsystem.controller;

import com.demo.bankingsystem.dao.request.CustomerLoginRequest;
import com.demo.bankingsystem.dao.request.CustomerRegisterRequest;
import com.demo.bankingsystem.dao.response.CustomerLoginResponse;
import com.demo.bankingsystem.dao.response.CustomerRegisterResponse;
import com.demo.bankingsystem.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerRegisterResponse registerCustomer(@RequestBody @Valid CustomerRegisterRequest customerRegisterRequest) {

        return customerService.registerCustomer(customerRegisterRequest);
    }


    @PostMapping("/login")
    public CustomerLoginResponse loginCustomer(@RequestBody @Valid CustomerLoginRequest customerLoginRequest) {

        return customerService.loginCustomer(customerLoginRequest);
    }
}
