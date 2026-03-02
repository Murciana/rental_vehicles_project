package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Car extends FourWheeled {
    private int doors; //3/5
    private int luggageCapacity;
}
