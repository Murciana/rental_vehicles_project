package com.accenture.rentalvehiclesapp.service.dto.response;

import com.accenture.rentalvehiclesapp.repository.entity.enums.ECarCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ERequiredLicence;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmissionCategory;

import java.util.UUID;

public record CarResponseDto(
        UUID id,
        String brand,
        String model,
        String color,
        int basicDailyRate,
        int mileage,
        Boolean active,
        Boolean removedFromPark,
        int seats,
        EFuelCategory fuel,
        ETransmissionCategory transmission,
        boolean airConditioning,
        ERequiredLicence licence,
        int doors,
        int luggageCapacity,
        ECarCategory category) {
}
