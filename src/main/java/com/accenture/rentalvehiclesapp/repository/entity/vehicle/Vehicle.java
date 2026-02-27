package com.accenture.rentalvehiclesapp.repository.entity.vehicle;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    protected String brand;
    protected String model;
    protected String color;
    protected int basicDailyRate;
    protected int mileage;
    protected boolean active;
    protected boolean removedFromPark;
}
