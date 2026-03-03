package com.accenture.rentalvehiclesapp.service.dto;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelType;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmission;
import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;

import java.util.UUID;

public record CarResponseDto(
        UUID id,
        String brand,
        String model,
        String color,
        int basicDailyRate,
        int mileage,
        int seats,
        EFuelType fuel,
        ETransmission transmission,
        boolean airConditioning,
        int doors,
        int luggageCapacity) {
}
