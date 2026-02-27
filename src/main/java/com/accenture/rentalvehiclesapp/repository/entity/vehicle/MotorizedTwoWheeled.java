package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import com.accenture.rentalvehiclesapp.repository.entity.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmission;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.ToString;

@ToString(callSuper = true)
@Entity
public class MotorizedTwoWheeled extends Vehicle {
    protected ETransmission transmission;

    @ManyToOne
    @JoinColumn
    protected Licence licence;
}
