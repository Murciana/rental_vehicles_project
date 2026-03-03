package com.accenture.rentalvehiclesapp.service.dto;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.Category;

public record CarPatchDto(
        String brand,
        String model,
        String color,
        Integer basicDailyRate,
        Integer mileage,
        Integer seats,
        EFuelCategory fuel,
        Category transmission,
        Boolean airConditioning,
        Integer doors,
        Integer luggageCapacity
) {
}

