package com.accenture.rentalvehiclesapp.repository.entity.vehicle;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EMotorcycleCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ERequiredLicence;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.*;

@NoArgsConstructor
@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Motorcycle extends PoweredTwoWheeler {
    private int cylinders;
    private int displacement; //cylindrée
    private int weight;
    private int power; //en kW
    private int seatHeight;
    private EMotorcycleCategory category;

    @PrePersist
    private void setLicence() {
        if(displacement <= 125 && power <=11)
            this.licence = ERequiredLicence.A1;
        else if(power >11 && power  <=35)
            this.licence = ERequiredLicence.A2;
        else
            this.licence = ERequiredLicence.A;
    }
}
