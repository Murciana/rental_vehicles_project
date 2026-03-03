package com.accenture.rentalvehiclesapp.service.dto;

import com.accenture.rentalvehiclesapp.repository.entity.enums.Category;
import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelCategory;

import java.util.UUID;

public record CarResponseDto(
        UUID id,
        String brand,
        String model,
        String color,
        int basicDailyRate,
        int mileage,
        int seats,
        EFuelCategory fuel,
        Category transmission,
        boolean airConditioning,
        int doors,
        int luggageCapacity) {
}
