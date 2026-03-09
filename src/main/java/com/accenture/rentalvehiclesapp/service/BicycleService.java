package com.accenture.rentalvehiclesapp.service;

import com.accenture.rentalvehiclesapp.exception.VehicleException;
import com.accenture.rentalvehiclesapp.service.dto.patch.BicyclePatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.BicycleRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.BicycleResponseDto;

import java.util.List;
import java.util.UUID;

public interface BicycleService {
    BicycleResponseDto save(BicycleRequestDto requestDto) throws VehicleException;

    List<BicycleResponseDto> findAll();

    BicycleResponseDto findById(UUID id);

    BicycleResponseDto patch(UUID id, BicyclePatchDto patchDto);

    public boolean existsById(UUID id);

    void delete(UUID id);

}
