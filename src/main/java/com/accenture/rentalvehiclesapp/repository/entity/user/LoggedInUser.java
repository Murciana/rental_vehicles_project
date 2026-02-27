package com.accenture.rentalvehiclesapp.repository.entity.user;

import com.accenture.rentalvehiclesapp.repository.entity.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class LoggedInUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    protected String lastName;
    protected String firstName;
    protected String email;
    protected String password;
    protected ERole role;

    public LoggedInUser(String lastName, String firstName, String email, String password, ERole role) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
