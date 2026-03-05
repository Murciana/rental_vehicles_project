package com.accenture.rentalvehiclesapp.service.dto.request;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EBicycleCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ECarCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmissionCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BicycleRequestDto(
        @NotBlank(message = "vehicule.brand.null")
        String brand,

        @NotBlank(message = "vehicule.model.null")
        String model,

        @NotBlank(message = "vehicule.color.null")
        String color,

        @NotNull(message = "vehicule.daily-basic-rate.null")
        @Min(value = 1, message = "vehicule.daily-basic-rate.invalid")
        int basicDailyRate,

        @NotNull(message = "vehicule.mileage.null")
        @Min(value = 0, message = "vehicule.mileage.invalid")
        int mileage,

        @NotNull(message = "bicycle.frame-size.null")
        @Min(value = 1, message = "bicycle.frame-size.invalid")
        int frameSize,

        @NotNull(message = "bicycle.weight.null")
        @Min(value = 1, message = "bicycle.weight.invalid")
        int weight,

        @NotNull(message = "bicycle.discbrakes.null")
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
