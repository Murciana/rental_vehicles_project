package com.accenture.rentalvehiclesapp.service.dto.response;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EMotorcycleCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ERequiredLicence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmissionCategory;

import java.util.UUID;

public record MotoResponseDto(
        UUID id,
        String brand,
        String model,
        String color,
        int basicDailyRate,
        int mileage,
        Boolean active,
        Boolean removedFromPark,
        ETransmissionCategory transmission,
        ERequiredLicence licence,
        int cylinders,
        int displacement,
        int weight,
        int power,
        int seatHeight,
        EMotorcycleCategory category


) {
}
