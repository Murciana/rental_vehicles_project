package com.accenture.rentalvehiclesapp.service.dto.request;

import com.accenture.rentalvehiclesapp.repository.entity.enums.EMotorcycleCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmissionCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MotoRequestDto(
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

        @NotNull(message = "twowheeler.transmission.null")
        ETransmissionCategory transmission,

        @NotNull(message = "motorcycle.cylinders.null")
        @Min(value = 1, message = "motorcycle.cylinders.invalid")
        @Max(value = 6, message = "motorcycle.cylinders.invalid")
        int cylinders,

        @NotNull(message = "motorcycle.displacement.null")
        @Min(value = 125, message = "motorcycle.displacement.invalid")
        int displacement,

        @NotNull(message = "motorcycle.weight.null")
        @Min(value = 1, message = "motorcycle.weight.invalid")
        int weight,

        @NotNull(message = "motorcycle.power.null")
        @Min(value = 7, message = "motorcycle.power.invalid")
        @Max(value = 220, message = "motorcycle.power.invalid")
        int power,

        @NotNull(message = "motorcycle.seat-height.null")
        @Min(value = 650, message = "motorcycle.seat-height.invalid")
        @Max(value = 950, message = "motorcycle.seat-height.invalid")
        int seatHeight,

        @NotNull(message = "motorcycle.category.null")
        EMotorcycleCategory category

) {
}
