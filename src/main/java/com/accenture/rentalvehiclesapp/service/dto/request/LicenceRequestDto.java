package com.accenture.rentalvehiclesapp.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LicenceRequestDto(
        @NotBlank(message = "licence.name.null")
        @Size(min = 1, max = 2, message = "licence.name.length")
        String name
) {
}
