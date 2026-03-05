package com.accenture.rentalvehiclesapp.security;


import com.accenture.rentalvehiclesapp.repository.CustomerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("customerSecurity")
public class CustomerSecurity {

    private final CustomerRepository customerRepository;

    public CustomerSecurity(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean isOwner(Authentication authentication, UUID id) {
        return customerRepository.existsByIdAndEmail(id, authentication.getName());
    }
}