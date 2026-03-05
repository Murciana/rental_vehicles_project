package com.accenture.rentalvehiclesapp.service;

import com.accenture.rentalvehiclesapp.exception.VehicleException;
import com.accenture.rentalvehiclesapp.service.dto.patch.MotoPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.MotoRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.MotoResponseDto;

import java.util.List;
import java.util.UUID;

public interface MotoService {
        MotoResponseDto save(MotoRequestDto requestDto) throws VehicleException;

    List<MotoResponseDto> findAll();

    MotoResponseDto findById(UUID id);

    MotoResponseDto patch(UUID id, MotoPatchDto patchDto);

    public boolean existsById(UUID id);

    void delete(UUID id);
}
