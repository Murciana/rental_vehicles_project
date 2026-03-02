package com.accenture.rentalvehiclesapp.repository.entity;

import com.accenture.rentalvehiclesapp.repository.entity.loggedInUser.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LicenceRepository extends JpaRepository<Licence, UUID> {
}
