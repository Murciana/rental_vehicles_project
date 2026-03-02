package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelType;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmission;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public abstract class FourWheeled extends Vehicle{
    protected int seats;
    protected EFuelType fuel;
    protected ETransmission transmission;
    protected boolean airConditioning;

    //Fonctionne comme le type dans tp radio
    @ManyToOne
    @JoinColumn
    protected Licence licence;
}
