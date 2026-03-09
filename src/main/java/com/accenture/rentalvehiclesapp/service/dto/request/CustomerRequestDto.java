package com.accenture.rentalvehiclesapp.service.dto.request;

import com.accenture.rentalvehiclesapp.service.dto.AddressDto;
import com.accenture.rentalvehiclesapp.utils.Messages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CustomerRequestDto(

        @NotBlank(message = Messages.USER_LAST_NAME_NULL)
        String lastName,

        @NotBlank(message = Messages.USER_FIRST_NAME_NULL)
        String firstName,

        @Email(message = Messages.USER_EMAIL_PATTERN_INVALID)
        @NotBlank(message = Messages.USER_EMAIL_NULL)
        String email,

        @Size(min = 8, max = 16, message = Messages.USER_PASSWORD_SIZE)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#_&§-]).{8,16}$",
                message = Messages.USER_PASSWORD_PATTERN_INVALID
        )
        @NotBlank(message = Messages.USER_PASSWORD_NULL)
        String password,

        @Past(message = Messages.CUSTOMER_BIRTHDATE_FUTURE)
        @NotNull(message = Messages.CUSTOMER_BIRTHDATE_NULL)
        LocalDate birthDate,

        @NotNull(message = Messages.CUSTOMER_ADDRESS_NULL)
        @Valid
        AddressDto address,

        List<UUID> licencesId
        ){
}
