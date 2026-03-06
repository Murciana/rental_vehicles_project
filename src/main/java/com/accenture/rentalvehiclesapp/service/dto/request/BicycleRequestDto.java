package com.accenture.rentalvehiclesapp.service.dto.request;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EBicycleCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BicycleRequestDto(
        @NotBlank(message = "vehicle.brand.null")
        String brand,

        @NotBlank(message = "vehicle.model.null")
        String model,

        @NotBlank(message = "vehicle.color.null")
        String color,

        @NotNull(message = "vehicle.daily-basic-rate.null")
        @Min(value = 1, message = "vehicle.daily-basic-rate.invalid")
        int basicDailyRate,

        @NotNull(message = "vehicle.mileage.null")
        @Min(value = 0, message = "vehicle.mileage.invalid")
        int mileage,

        @NotNull(message = "bicycle.frame-size.null")
        @Min(value = 1, message = "bicycle.frame-size.invalid")
        int frameSize,

        @NotNull(message = "bicycle.weight.null")
        @Min(value = 1, message = "bicycle.weight.invalid")
        int weight,

        @NotNull(message = "bicycle.disc-brakes.null")
        Boolean discBrakes,

        @NotNull(message = "bicycle.electric.null")
        Boolean electric,

        @Min(value = 0, message = "bicycle.autonomy.invalid")
        Integer autonomy,

        @Min(value = 0, message = "bicycle.battery-capacity.invalid")
        Integer batteryCapacity,

        @NotNull(message = "bicycle.category.null")
        EBicycleCategory category
) {
}
