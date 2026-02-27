package com.accenture.rentalvehiclesapp.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDto(
        @NotBlank(message = "address.street.null")
        String street,

        @NotBlank(message = "address.postcode.null")
        @Pattern(regexp = "^[0-9]{5}$", message = "address.postcode.invalid")
        String postCode,

        @NotBlank(message = "address.city.null")
        String city
) { }
