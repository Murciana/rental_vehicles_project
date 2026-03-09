package com.accenture.rentalvehiclesapp.service.dto.request;

import com.accenture.rentalvehiclesapp.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LicenceRequestDto(
        @NotBlank(message = Messages.LICENCE_NAME_NULL)
        @Size(min = 1, max = 2, message = Messages.LICENCE_NAME_LENGTH)
        String name
) {
}
