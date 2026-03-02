package com.accenture.rentalvehiclesapp.repository.entity.loggedInUser;

import com.accenture.rentalvehiclesapp.repository.entity.enums.ERole;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class Admin extends LoggedInUser{
    private String position;

}
