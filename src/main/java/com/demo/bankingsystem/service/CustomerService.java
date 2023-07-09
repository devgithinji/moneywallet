package com.demo.bankingsystem.service;

import com.demo.bankingsystem.dao.request.CustomerLoginRequest;
import com.demo.bankingsystem.dao.request.CustomerRegisterRequest;
import com.demo.bankingsystem.dao.response.CustomerLoginResponse;
import com.demo.bankingsystem.dao.response.CustomerRegisterResponse;

public interface CustomerService {


    CustomerRegisterResponse registerCustomer(CustomerRegisterRequest customerRegisterRequest);

    CustomerLoginResponse loginCustomer(CustomerLoginRequest customerLoginRequest);
}
