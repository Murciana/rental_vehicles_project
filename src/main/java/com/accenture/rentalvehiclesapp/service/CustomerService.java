package com.accenture.rentalvehiclesapp.service;

import com.accenture.rentalvehiclesapp.exception.CustomerException;
import com.accenture.rentalvehiclesapp.service.dto.request.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CustomerResponseDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerResponseDto save(CustomerRequestDto requestDto) throws CustomerException;

    List<CustomerResponseDto> findAll();

    CustomerResponseDto findById(UUID id);

    void delete(UUID id);

    CustomerResponseDto patch(UUID id, CustomerRequestDto requestDto);

    CustomerResponseDto findByEmail(String email);
}
