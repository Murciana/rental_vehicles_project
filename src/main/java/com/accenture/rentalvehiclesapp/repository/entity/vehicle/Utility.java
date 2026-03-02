package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Utility extends FourWheeled{
    private int maxLoad;
    private int  gvwr; //Gross Vehicle Weight Rating
    private int capacity; //en 3
}
