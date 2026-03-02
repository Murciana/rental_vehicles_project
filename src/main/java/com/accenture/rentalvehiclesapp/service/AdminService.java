package com.accenture.rentalvehiclesapp.service;

import com.accenture.rentalvehiclesapp.exception.AdminException;
import com.accenture.rentalvehiclesapp.service.dto.AdminRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.AdminResponseDto;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    AdminResponseDto save(AdminRequestDto requestDto) throws AdminException;

    List<AdminResponseDto> findAll();

    AdminResponseDto findById(UUID id);

    void delete(UUID id);

    AdminResponseDto patch(UUID id, AdminRequestDto requestDto);
}
