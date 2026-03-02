package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmission;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class MotorizedTwoWheeled extends Vehicle {
    protected ETransmission transmission;

    @ManyToOne
    @JoinColumn
    protected Licence licence;
}
