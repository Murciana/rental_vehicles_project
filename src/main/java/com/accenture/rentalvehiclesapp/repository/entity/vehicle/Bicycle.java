package com.accenture.rentalvehiclesapp.repository.entity.vehicle;


import com.accenture.rentalvehiclesapp.repository.entity.enums.EBicycleCategory;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Bicycle extends Vehicle {
    private int frameSize;
    private int wieght;
    private boolean electric;
    private boolean discBrakes;
    private int autonomy; // si electric - en heures
    private int batteryCapacity; //si electric
    private EBicycleCategory category;
}
