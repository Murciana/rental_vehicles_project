package com.accenture.rentalvehiclesapp.service;

import com.accenture.rentalvehiclesapp.exception.LicenceException;
import com.accenture.rentalvehiclesapp.service.dto.request.LicenceRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.LicenceResponseDto;

import java.util.List;
import java.util.UUID;

public interface LicenceService {
    LicenceResponseDto save(LicenceRequestDto requestDto) throws LicenceException;

    List<LicenceResponseDto> findAll();

    LicenceResponseDto findById(UUID id);
}
