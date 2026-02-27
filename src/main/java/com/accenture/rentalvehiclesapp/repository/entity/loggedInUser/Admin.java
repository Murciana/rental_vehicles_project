package com.accenture.rentalvehiclesapp.repository.entity.loggedInUser;

import com.accenture.rentalvehiclesapp.repository.entity.enums.ERole;
import jakarta.persistence.Entity;
import lombok.ToString;

@ToString(callSuper = true)
@Entity
public class Admin extends LoggedInUser{
    private String position;

    public Admin(String lastName, String firstName, String email, String password, ERole role, String position) {
        super(lastName, firstName, email, password, role);
        this.position = position;
    }
}
