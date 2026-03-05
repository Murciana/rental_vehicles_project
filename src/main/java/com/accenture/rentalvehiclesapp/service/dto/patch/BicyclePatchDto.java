package com.accenture.rentalvehiclesapp.service.dto.patch;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EBicycleCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.EMotorcycleCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmissionCategory;

public record BicyclePatchDto(
        String brand,
        String model,
        String color,
        Integer basicDailyRate,
        Integer mileage,
        Boolean active,
        Boolean removedFromPark,
        Integer frameSize,
        Integer weight,
        Boolean electric,
        Boolean discBrakes,
        Integer autonomy,
        Integer batteryCapacity,
        EBicycleCategory category
) {
}
