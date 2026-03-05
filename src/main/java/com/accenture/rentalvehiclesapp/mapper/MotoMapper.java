package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Car;
import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Motorcycle;
import com.accenture.rentalvehiclesapp.service.dto.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MotoMapper {
    Motorcycle toEntity(MotoRequestDto requestDto);
    MotoResponseDto toMotoResponseDto(Motorcycle motorcycle);
    MotoPatchDto toMotoPatchDto(Motorcycle motorcycle);
}
