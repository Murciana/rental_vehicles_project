package com.accenture.rentalvehiclesapp.service;

import com.accenture.rentalvehiclesapp.exception.LicenceException;
import com.accenture.rentalvehiclesapp.service.dto.LicenceRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.LicenceResponseDto;

public interface LicenceService {
    LicenceResponseDto save(LicenceRequestDto requestDto) throws LicenceException;
}
