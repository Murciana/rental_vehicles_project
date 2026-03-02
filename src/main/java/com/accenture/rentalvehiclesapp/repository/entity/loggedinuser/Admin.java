package com.accenture.rentalvehiclesapp.repository.entity.loggedinuser;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class Admin extends LoggedInUser{
    private String position;

}
