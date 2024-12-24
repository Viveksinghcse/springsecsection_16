package com.viveksinghspringsecurity.controller;

import com.viveksinghspringsecurity.model.Accounts;
import com.viveksinghspringsecurity.model.Customer;
import com.viveksinghspringsecurity.repository.AccountsRepository;
import com.viveksinghspringsecurity.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AccountController {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    public AccountController(AccountsRepository accountsRepository,
                             CustomerRepository customerRepository) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()){
            Accounts accounts = accountsRepository.findByCustomerId(optionalCustomer.get().getId());
            if (accounts != null) {
                return accounts;
            } else {
                return null;
            }

        } else {
     return null;
    }
    }
}
