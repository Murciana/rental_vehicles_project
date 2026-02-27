package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.AdminException;
import com.accenture.rentalvehiclesapp.repository.entity.AdminRepository;
import com.accenture.rentalvehiclesapp.repository.entity.loggedInUser.Admin;
import com.accenture.rentalvehiclesapp.repository.entity.loggedInUser.Customer;
import com.accenture.rentalvehiclesapp.service.AdminService;
import com.accenture.rentalvehiclesapp.service.dto.AdminRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.AdminResponseDto;
import com.accenture.rentalvehiclesapp.mapper.AdminMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final MessageSourceAccessor messages;
    private final AdminMapper adminMapper;

    @Override
    public AdminResponseDto save(AdminRequestDto requestDto) throws AdminException {
        check(requestDto);

        Admin newAdmin = adminMapper.ToEntity(requestDto);
        Admin saved = adminRepository.save(newAdmin);

        return adminMapper.toAdminResponseDto(saved);
    }

    private void check(AdminRequestDto requestDto) {
    }
}
