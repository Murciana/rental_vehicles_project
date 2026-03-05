package com.accenture.rentalvehiclesapp.service.dto.response;

import java.util.UUID;

public record AdminResponseDto(
        UUID id,
        String lastName,
        String firstName,
        String email,
        String position

        ) { }
