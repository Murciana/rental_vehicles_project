package com.accenture.rentalvehiclesapp.repository;

import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MotoRepository extends JpaRepository<Motorcycle, UUID> {
    @Query(
            """
            SELECT DISTINCT m FROM Motorcycle m
            ORDER BY m.removedFromPark ASC, m.active DESC
            """
    )
    List<Motorcycle> findAll();
}
