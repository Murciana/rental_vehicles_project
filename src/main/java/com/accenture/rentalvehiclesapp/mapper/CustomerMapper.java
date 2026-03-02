package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Customer;
import com.accenture.rentalvehiclesapp.service.dto.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.CustomerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "role", constant = "CUSTOMER") // Force le rôle à CUSTOMER
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDateTime.now())")

    Customer toEntity(CustomerRequestDto requestDto);
    CustomerResponseDto toCustomerResponseDto(Customer customer);
}
