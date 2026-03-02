package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.Licence;
import com.accenture.rentalvehiclesapp.service.dto.LicenceRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.LicenceResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LicenceMapper {
    Licence toEntity(LicenceRequestDto requestDto);

    LicenceResponseDto toLicenceResponseDto(Licence saved);
}
