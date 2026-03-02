package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.AdminException;
import com.accenture.rentalvehiclesapp.exception.CustomerException;
import com.accenture.rentalvehiclesapp.repository.entity.AdminRepository;
import com.accenture.rentalvehiclesapp.repository.entity.loggedInUser.Admin;
import com.accenture.rentalvehiclesapp.service.AdminService;
import com.accenture.rentalvehiclesapp.service.dto.AdminRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.AdminResponseDto;
import com.accenture.rentalvehiclesapp.mapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {
    private static final String ADMIN_NOT_FOUND = "admin.id.notfound";

    private final AdminRepository adminRepository;
    private final MessageSourceAccessor messages;
    private final AdminMapper adminMapper;

    @Override
    public AdminResponseDto save(AdminRequestDto requestDto) throws AdminException {
        check(requestDto);

        Admin newAdmin = adminMapper.toEntity(requestDto);
        Admin saved = adminRepository.save(newAdmin);

        return adminMapper.toAdminResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminResponseDto> findAll() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toAdminResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AdminResponseDto findById(UUID id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(ADMIN_NOT_FOUND)));
        return adminMapper.toAdminResponseDto(admin);
    }

    private void check(AdminRequestDto requestDto) {
        if (requestDto == null)
            throw new AdminException(messages.getMessage("user.null"));
        if (requestDto.firstName() == null)
            throw new AdminException(messages.getMessage("user.firstname.null"));
        if (requestDto.lastName() == null)
            throw new AdminException(messages.getMessage("user.lastname.null"));
        if (requestDto.email() == null)
            throw new AdminException(messages.getMessage("user.email.null"));
        // Vérification que l'email n'existe pas déjà
        if (adminRepository.existsByEmail(requestDto.email()))
            throw new AdminException(messages.getMessage("user.email.duplicate"));
        if (requestDto.password() == null)
            throw new AdminException(messages.getMessage("user.password.null"));
        if (requestDto.position() == null)
            throw new AdminException(messages.getMessage("admin.position.null"));
    }
}
