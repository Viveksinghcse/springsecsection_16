package com.viveksinghspringsecurity.controller;

import com.viveksinghspringsecurity.model.AccountTransactions;
import com.viveksinghspringsecurity.model.Customer;
import com.viveksinghspringsecurity.repository.AccountTransactionsRepository;
import com.viveksinghspringsecurity.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BalanceController {
    private final AccountTransactionsRepository accountTransactionsRepository;
    private final CustomerRepository customerRepository ;

    public BalanceController(AccountTransactionsRepository accountTransactionsRepository,
                             CustomerRepository customerRepository) {
        this.accountTransactionsRepository = accountTransactionsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);

        if (optionalCustomer.isPresent()){
            List<AccountTransactions> accountTransactions =
                    accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(optionalCustomer.get().getId());
            if (accountTransactions != null) {
                return accountTransactions;
            } else {
                return null;
            }
        }else {
            return null;
        }

    }
}
