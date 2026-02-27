package com.accenture.rentalvehiclesapp.repository.entity.loggedInUser;

import com.accenture.rentalvehiclesapp.repository.entity.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ERole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@Entity
public class Customer extends LoggedInUser {
    private LocalDate birthDate;
    private LocalDateTime registrationDate;

    @ManyToOne()
    private List<Licence> licences;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private boolean disabled;

    public Customer(String lastName, String firstName, String email, String password, ERole role, LocalDate birthDate, LocalDate registrationDate, Address address, boolean disabled) {
        super(lastName, firstName, email, password, role);
        this.birthDate = birthDate;
        this.registrationDate = LocalDateTime.now();
        this.address = address;
        this.disabled = disabled;
    }
}
