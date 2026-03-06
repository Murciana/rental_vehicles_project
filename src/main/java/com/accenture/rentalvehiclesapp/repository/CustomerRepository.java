package com.accenture.rentalvehiclesapp.repository;

import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email);
}
