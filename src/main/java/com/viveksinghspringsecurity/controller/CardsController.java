package com.viveksinghspringsecurity.controller;

import com.viveksinghspringsecurity.model.Cards;
import com.viveksinghspringsecurity.model.Customer;
import com.viveksinghspringsecurity.repository.CardsRepository;
import com.viveksinghspringsecurity.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class CardsController {
    private final CardsRepository cardsRepository;
    private final CustomerRepository customerRepository ;

    public CardsController(CardsRepository cardsRepository,
                           CustomerRepository customerRepository) {
        this.cardsRepository = cardsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);

        if (optionalCustomer.isPresent()){
            List<Cards> cards =
                    cardsRepository.findByCustomerId(optionalCustomer.get().getId());
            if (cards != null ) {
                return cards;
            }else {
                return null;
            }
        }else {
            return null;
        }

    }
}
