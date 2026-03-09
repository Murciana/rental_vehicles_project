package com.accenture.rentalvehiclesapp.service.dto;

import com.accenture.rentalvehiclesapp.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDto(
        @NotBlank(message = Messages.ADDRESS_STREET_NULL)
        String street,

        @NotBlank(message = Messages.ADDRESS_POSTCODE_NULL)
        @Pattern(regexp = "^\\d{5}$", message = Messages.ADDRESS_POSTCODE_PATTERN_INVALID)
        String postCode,

        @NotBlank(message = Messages.ADDRESS_CITY_NULL)
        String city
) { }
