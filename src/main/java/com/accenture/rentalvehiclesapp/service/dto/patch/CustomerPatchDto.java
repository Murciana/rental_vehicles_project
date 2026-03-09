package com.accenture.rentalvehiclesapp.service.dto.patch;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EBicycleCategory;
import com.accenture.rentalvehiclesapp.service.dto.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CustomerPatchDto(

        String lastName,


        String firstName,


        String email,

        String password,


        LocalDate birthDate,

        AddressDto address,

        List<UUID> licencesId
) {
}
