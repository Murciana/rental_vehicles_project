package com.accenture.rentalvehiclesapp.repository.entity.vehicle;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.ToString;

@ToString(callSuper = true)
@Entity
public class Bicycle extends Vehicle {
    private int frameSize;
    private int wieght;
    private boolean electric;
    private boolean discBrakes;
    private int autonomy; // si electric - en heures
    private int batteryCapacity; //si electric
}
