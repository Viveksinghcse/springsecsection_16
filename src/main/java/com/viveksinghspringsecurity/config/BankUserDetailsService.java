package com.viveksinghspringsecurity.config;

import com.viveksinghspringsecurity.model.Customer;
import com.viveksinghspringsecurity.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public BankUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User details not found for the user" + username));

        List<GrantedAuthority> authorities = customer.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
//        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));

        return new User(customer.getEmail(), customer.getPwd(), authorities);
    }

}
