package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import jakarta.persistence.Entity;

@Entity
public class Car extends FourWheeled {
    private int doors; //3/5
    private int luggageCapacity;
}
