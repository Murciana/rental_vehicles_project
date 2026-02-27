package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import jakarta.persistence.Entity;

@Entity
public class Rv extends FourWheeled{
    private int height;
    private int  gvwr; //Gross Vehicle Weight Rating
    private int numberOfBeds;
    private boolean kitchenEquipment;
    private boolean bedLinen;
    private boolean refrigeratorEquipment;
    private boolean showerEquipment;
}
