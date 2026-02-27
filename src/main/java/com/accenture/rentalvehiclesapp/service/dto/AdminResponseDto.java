package com.accenture.rentalvehiclesapp.service.dto;

import java.util.UUID;

public record AdminResponseDto(
        UUID id,
        String lastName,
        String firstName,
        String email,
        String position

        ) { }
