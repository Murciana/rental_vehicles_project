package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Motorcycle extends MotorizedTwoWheeled {
    private int cylinders;
    private int displacement;
    private int weight;
    private int power; //en kW
    private int seatHeight;

}
