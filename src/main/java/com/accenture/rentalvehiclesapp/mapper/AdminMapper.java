package com.accenture.rentalvehiclesapp.mapper;

import com.accenture.rentalvehiclesapp.repository.entity.loggedInUser.Admin;
import com.accenture.rentalvehiclesapp.service.dto.AdminRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.AdminResponseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin toEntity(AdminRequestDto requestDto);
    AdminResponseDto toAdminResponseDto(Admin admin);
}
