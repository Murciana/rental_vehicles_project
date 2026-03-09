package com.accenture.rentalvehiclesapp.repository;

import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {

    @Query(
            """
            SELECT DISTINCT c FROM Car c 
            ORDER BY c.removedFromPark ASC, c.active DESC
            """
    )
    List<Car> findAll();

}
