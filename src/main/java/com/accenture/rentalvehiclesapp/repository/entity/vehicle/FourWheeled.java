package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import com.accenture.rentalvehiclesapp.repository.entity.enums.Category;
import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelCategory;
import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public abstract class FourWheeled extends Vehicle{
    protected int seats;
    protected EFuelCategory fuel;
    protected Category transmission;
    protected boolean airConditioning;

    //Fonctionne comme le type dans tp radio
    @ManyToOne
    @JoinColumn
    protected Licence licence;
}
