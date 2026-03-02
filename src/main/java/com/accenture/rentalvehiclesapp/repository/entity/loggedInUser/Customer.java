package com.accenture.rentalvehiclesapp.repository.entity.loggedInUser;

import com.accenture.rentalvehiclesapp.repository.entity.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ERole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
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
