package com.accenture.rentalvehiclesapp.service.dto.request;

import com.accenture.rentalvehiclesapp.repository.entity.enums.ECarCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.EFuelCategory;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ETransmissionCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarRequestDto(
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

        @NotNull(message = "fourwheeled.seats.null")
        @Min(value = 2, message = "fourwheeled.seats.invalid")
        @Max(value = 17, message = "fourwheeled.seats.invalid")
        int seats,

        @NotNull(message = "fourwheeled.fuel.null")
        EFuelCategory fuel,

        @NotNull(message = "fourwheeled.transmission.null")
        ETransmissionCategory transmission,

        @NotNull(message = "fourwheeled.air-conditioning.null")
        Boolean airConditioning,

        @NotNull(message = "car.doors.null")
        @Min(value = 3, message = "car.doors.invalid")
        @Max(value = 5, message = "car.doors.invalid")
        int doors,

        @NotNull(message = "cars.luggage-capacity.null")
        @Min(value = 0, message = "cars.luggage-capacity.invalid")
        int luggageCapacity,

        @NotNull(message = "cars.category.null")
        ECarCategory category
) {
}
