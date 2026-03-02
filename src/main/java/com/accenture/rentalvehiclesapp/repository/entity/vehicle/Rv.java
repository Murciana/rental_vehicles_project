package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Rv extends FourWheeled{
    private int height;
    private int  gvwr; //Gross Vehicle Weight Rating
    private int numberOfBeds;
    private boolean kitchenEquipment;
    private boolean bedLinen;
    private boolean refrigeratorEquipment;
    private boolean showerEquipment;
}
