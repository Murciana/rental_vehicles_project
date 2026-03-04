package com.accenture.rentalvehiclesapp.service.dto;

import com.accenture.rentalvehiclesapp.repository.entity.enums.ECarCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmissionCategory;

public record CarPatchDto(
        String brand,
        String model,
        String color,
        Integer basicDailyRate,
        Integer mileage,
        Integer seats,
        EFuelCategory fuel,
        ETransmissionCategory transmission,
        Boolean airConditioning,
        Integer doors,
        Integer luggageCapacity,
        ECarCategory category
) {
}

