package com.accenture.rentalvehiclesapp.repository.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.ToString;

@ToString(callSuper = true)
@Entity
public class Admin extends LoggedInUser{
    private String function;
}
