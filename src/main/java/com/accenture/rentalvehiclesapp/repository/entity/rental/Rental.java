package com.accenture.rentalvehiclesapp.repository.entity.rental;

import com.accenture.rentalvehiclesapp.repository.entity.enums.ERentalStatus;
import com.accenture.rentalvehiclesapp.repository.entity.user.Customer;
import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//TOUT PART DE LA LOCATION (Comme les emissiosn dans TP radio)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Vehicle vehicle;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Accessory> accessories;

    private LocalDate startDate;
    private LocalDate endDate;
    private int kilometersTraveled;
    private int totalAmount;
    private LocalDateTime confirmationDate;
    private ERentalStatus rentalStatus;

}
