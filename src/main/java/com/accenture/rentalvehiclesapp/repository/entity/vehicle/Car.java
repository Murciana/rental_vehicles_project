package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import com.accenture.rentalvehiclesapp.repository.entity.enums.ECarCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ERequiredLicence;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Car extends FourWheeled {
    private int doors; //3/5
    private int luggageCapacity;
    private ECarCategory category;

    @PrePersist
    private void setLicence() {
        if (seats <= 9)
            this.licence = ERequiredLicence.B;
        if (seats >= 10)
            this.licence = ERequiredLicence.D1;
    }

}
