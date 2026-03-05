package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Motorcycle;
import com.accenture.rentalvehiclesapp.service.dto.patch.MotoPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.MotoRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.MotoResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MotoMapper {
    Motorcycle toEntity(MotoRequestDto requestDto);
    MotoResponseDto toMotoResponseDto(Motorcycle motorcycle);
    MotoPatchDto toMotoPatchDto(Motorcycle motorcycle);
}
