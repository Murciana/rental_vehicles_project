package com.accenture.rentalvehiclesapp.repository.entity.loggedinuser;

import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Customer extends LoggedInUser {
    private LocalDate birthDate;
    private LocalDateTime registrationDate;

    @ManyToMany
    @JoinTable(
            name = "customer_licences",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "licence_id")
    )
    private List<Licence> licences;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private boolean disabled = false;

}
