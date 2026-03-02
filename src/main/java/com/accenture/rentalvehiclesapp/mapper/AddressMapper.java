package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Address;
import com.accenture.rentalvehiclesapp.service.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toAddressDto(Address address);

    Address toEntity(AddressDto addressDto);
}
