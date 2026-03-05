package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Admin;
import com.accenture.rentalvehiclesapp.service.dto.request.AdminRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.AdminResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    @Mapping(target = "role", constant = "ADMIN") // Force le rôle à ADMIN

    Admin toEntity(AdminRequestDto requestDto);
    AdminResponseDto toAdminResponseDto(Admin admin);
}
