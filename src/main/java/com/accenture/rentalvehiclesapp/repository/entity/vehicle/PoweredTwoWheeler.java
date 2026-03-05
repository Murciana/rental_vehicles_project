package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import com.accenture.rentalvehiclesapp.repository.entity.enums.ERequiredLicence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmissionCategory;
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
public class PoweredTwoWheeler extends Vehicle {
    protected ETransmissionCategory transmission;
    protected ERequiredLicence licence;
}
