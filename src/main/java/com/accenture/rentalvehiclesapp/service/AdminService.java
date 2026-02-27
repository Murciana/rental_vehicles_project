package com.accenture.rentalvehiclesapp.service;

import com.accenture.rentalvehiclesapp.exception.AdminException;
import com.accenture.rentalvehiclesapp.service.dto.AdminRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.AdminResponseDto;

public interface AdminService {
    AdminResponseDto save(AdminRequestDto requestDto) throws AdminException;
}
