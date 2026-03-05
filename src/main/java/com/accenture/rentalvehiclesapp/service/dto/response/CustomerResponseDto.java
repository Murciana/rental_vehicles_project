package com.accenture.rentalvehiclesapp.service.dto.response;

import com.accenture.rentalvehiclesapp.service.dto.AddressDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerResponseDto(
        UUID id,
        String lastName,
        String firstName,
        String email,

        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birthDate,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime registrationDate,

        AddressDto address,

        List<LicenceResponseDto> licences
        )
{ }
