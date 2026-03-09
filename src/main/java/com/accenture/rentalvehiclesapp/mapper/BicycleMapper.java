package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Bicycle;
import com.accenture.rentalvehiclesapp.service.dto.patch.BicyclePatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.BicycleRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.BicycleResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BicycleMapper {
    Bicycle toEntity(BicycleRequestDto requestDto);
    BicycleResponseDto toBicycleResponseDto(Bicycle bicycle);
    BicyclePatchDto toBicyclePatchDto(Bicycle bicycle);
}
