package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Car;
import com.accenture.rentalvehiclesapp.service.dto.CarRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.CarResponseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    Car toEntity(CarRequestDto requestDto);
    CarResponseDto toCarResponseDto(Car car);
}
