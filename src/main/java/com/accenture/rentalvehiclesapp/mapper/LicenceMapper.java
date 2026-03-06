package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import com.accenture.rentalvehiclesapp.service.dto.request.LicenceRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.LicenceResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LicenceMapper {
    Licence toEntity(LicenceRequestDto requestDto);

    LicenceResponseDto toLicenceResponseDto(Licence licence);
}
