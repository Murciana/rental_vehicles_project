package com.accenture.rentalvehiclesapp.service;

import com.accenture.rentalvehiclesapp.exception.CustomerException;
import com.accenture.rentalvehiclesapp.service.dto.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.CustomerResponseDto;

public interface CustomerService {
    CustomerResponseDto save(CustomerRequestDto requestDto) throws CustomerException;
}
