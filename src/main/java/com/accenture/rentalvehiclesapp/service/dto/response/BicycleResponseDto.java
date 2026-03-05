package com.accenture.rentalvehiclesapp.service.dto.response;

import com.accenture.rentalvehiclesapp.repository.entity.enums.*;

import java.util.UUID;

public record BicycleResponseDto(
        UUID id,
        String brand,
        String model,
        String color,
        int basicDailyRate,
        int mileage,
        Boolean active,
        Boolean removedFromPark,
        int frameSize,
        int weight,
        boolean electric,
        boolean discBrakes,
        Integer autonomy,
        Integer batteryCapacity,
        EBicycleCategory category) {
}
