package com.accenture.rentalvehiclesapp.repository.entity.loggedInUser;

import jakarta.persistence.Entity;
import lombok.ToString;

@ToString(callSuper = true)
@Entity
public class Admin extends LoggedInUser{
    private String function;
}
