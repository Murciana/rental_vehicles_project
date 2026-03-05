package com.accenture.rentalvehiclesapp.repository.entity;

import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Admin;
import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BicycleRepository extends JpaRepository<Bicycle, UUID> {
}
