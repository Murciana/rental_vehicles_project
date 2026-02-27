package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import com.accenture.rentalvehiclesapp.repository.entity.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelType;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmission;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.ToString;

@ToString(callSuper = true)
@Entity
public abstract class FourWheeled extends Vehicle{
    protected int seats;
    protected EFuelType fuel;
    protected ETransmission transmission;
    protected boolean airConditioning;

    //Fonctionne comme le type dans tp rado
    @ManyToOne
    @JoinColumn
    protected Licence licence;
}
