package com.accenture.rentalvehiclesapp.service.dto;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EMotorcycleCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmissionCategory;

public record MotoPatchDto(
        String brand,
        String model,
        String color,
        Integer basicDailyRate,
        Integer mileage,
        Boolean active,
        Boolean removedFromPark,
        ETransmissionCategory transmission,
        Integer cylinders,
        Integer displacement,
        Integer weight,
        Integer power,
        Integer seatHeight,
        EMotorcycleCategory category
) {
}
