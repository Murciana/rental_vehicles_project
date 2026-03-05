package com.accenture.rentalvehiclesapp.service;

import com.accenture.rentalvehiclesapp.exception.VehicleException;
import com.accenture.rentalvehiclesapp.service.dto.patch.CarPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.CarRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CarResponseDto;

import java.util.List;
import java.util.UUID;

public interface CarService {
    CarResponseDto save(CarRequestDto requestDto) throws VehicleException;

    List<CarResponseDto> findAll();

    CarResponseDto findById(UUID id);

    CarResponseDto patch(UUID id, CarPatchDto patchDto);

    public boolean existsById(UUID id);

    void delete(UUID id);

}
