package com.demo.bankingsystem.auth;

import com.demo.bankingsystem.exception.ResourceNotFoundException;
import com.demo.bankingsystem.respository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerAuthService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerAuthService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String idNumber) throws UsernameNotFoundException {
        return customerRepository.findCustomerByIdNumber(idNumber).orElseThrow(() -> new ResourceNotFoundException("customer", "id no", idNumber));
    }
}
