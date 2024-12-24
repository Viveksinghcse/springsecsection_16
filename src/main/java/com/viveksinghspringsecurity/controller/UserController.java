package com.viveksinghspringsecurity.controller;

import com.viveksinghspringsecurity.model.Customer;
import com.viveksinghspringsecurity.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

@Autowired
    private final CustomerRepository customerRepository;

    public UserController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }

}

//..................................................
