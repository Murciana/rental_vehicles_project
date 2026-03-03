package com.accenture.rentalvehiclesapp.service.dto;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelType;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmission;

public record CarPatchDto(
        String brand,
        String model,
        String color,
        Integer basicDailyRate,
        Integer mileage,
        Integer seats,
        EFuelType fuel,
        ETransmission transmission,
        Boolean airConditioning,
        Integer doors,
        Integer luggageCapacity
) {
}

