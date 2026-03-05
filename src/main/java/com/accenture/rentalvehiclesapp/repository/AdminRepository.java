package com.accenture.rentalvehiclesapp.repository.entity;

import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    boolean existsByEmail(String email);
}
