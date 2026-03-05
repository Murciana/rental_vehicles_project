package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Car;
import com.accenture.rentalvehiclesapp.service.dto.patch.CarPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.CarRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CarResponseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    Car toEntity(CarRequestDto requestDto);
    CarResponseDto toCarResponseDto(Car car);
    CarPatchDto toCarPatchDto(Car car);
}
