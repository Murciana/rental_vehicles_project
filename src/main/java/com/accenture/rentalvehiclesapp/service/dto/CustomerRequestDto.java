package com.accenture.rentalvehiclesapp.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.Period;

public record CustomerRequestDto(

        @NotBlank(message = "user.lastname.null")
        String lastName,

        @NotBlank(message = "user.firstname.null")
        String firstName,

        @Email(message = "user.email.invalid")
        @NotBlank(message = "user.email.null")
        String email,

        @Size(min = 8, max = 16, message = "user.password.size")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#_&§-]).{8,16}$",
                message = "user.password.invalid"
        )
        @NotBlank(message = "user.password.null")
        String password,

        @Past(message = "customer.birthdate.future")
        @NotNull(message = "customer.birthdate.null")
        //majorité gerée dans le service pour l'instant
        LocalDate birthDate,

        @NotNull(message = "customer.address.null")
        @Valid
        AddressDto address
        ){
}
