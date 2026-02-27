package com.accenture.rentalvehiclesapp.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminRequestDto(
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

        @NotBlank(message = "admin.position.null")
        String position
        ) { }
