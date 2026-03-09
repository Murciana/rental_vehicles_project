package com.accenture.rentalvehiclesapp.repository;

import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LicenceRepository extends JpaRepository<Licence, UUID> {
}
