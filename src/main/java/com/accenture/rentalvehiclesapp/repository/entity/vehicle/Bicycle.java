package com.accenture.rentalvehiclesapp.repository.entity.vehicle;


import com.accenture.rentalvehiclesapp.repository.entity.enums.EBicycleCategory;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Bicycle extends Vehicle {
    private int frameSize;
    private int weight;
    private boolean electric;
    private boolean discBrakes; //Freins à disque (O/N)
    private int autonomy; // si electric - en heures
    private int batteryCapacity; //si electric
    private EBicycleCategory category;
}
