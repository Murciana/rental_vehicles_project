package com.accenture.rentalvehiclesapp.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

//Fonctionne comme les radios et types d'emissions
@Entity
@Data
@NoArgsConstructor
public class Licence {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    public Licence(String name) {
        this.name = name;
    }
}
