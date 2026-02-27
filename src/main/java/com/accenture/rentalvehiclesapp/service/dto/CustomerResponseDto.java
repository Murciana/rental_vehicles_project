package com.accenture.rentalvehiclesapp.service.dto;

import com.accenture.rentalvehiclesapp.repository.entity.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.accenture.rentalvehiclesapp.repository.entity.enums.ERole.CUSTOMER;

public record CustomerResponseDto(
        UUID id,
        String lastName,
        String firstName,
        String email,

        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birthDate,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime registrationDate,

        AddressDto address
        )
{ }
