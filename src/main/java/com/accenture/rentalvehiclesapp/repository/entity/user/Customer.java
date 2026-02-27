package com.accenture.rentalvehiclesapp.repository.entity.user;

import com.accenture.rentalvehiclesapp.repository.entity.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.rental.Rental;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString(callSuper = true)
@Entity
public class Customer extends LoggedInUser{
    private LocalDate birthDate;
    private LocalDate registrationDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    @Column(name = "licence_id")
    private List<Licence> licences;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private boolean disabled;


}
