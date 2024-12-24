package com.viveksinghspringsecurity.controller;

import com.viveksinghspringsecurity.model.Customer;
import com.viveksinghspringsecurity.model.Loans;
import com.viveksinghspringsecurity.repository.CustomerRepository;
import com.viveksinghspringsecurity.repository.LoanRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LoansController {
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository ;

    public LoansController(LoanRepository loanRepository,
                           CustomerRepository customerRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myLoans")
    @PostAuthorize("hasRole('USER')")
    public List<Loans> getLoanDetails(@RequestParam  String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()){
            List<Loans> loans =
                    loanRepository.findByCustomerIdOrderByStartDtDesc(optionalCustomer.get().getId());
            if (loans != null) {
                return loans;
            } else {
                return null;
            }
        }
    return null;
    }
}
