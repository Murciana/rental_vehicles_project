package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.AdminException;
import com.accenture.rentalvehiclesapp.repository.entity.AdminRepository;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Admin;
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
        verifyDto(requestDto);

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


    @Override
    public void delete(UUID id) {
        if (!adminRepository.existsById(id))
            throw new EntityNotFoundException(messages.getMessage(ADMIN_NOT_FOUND));
        if (adminRepository.findAll().size() == 1) {
            throw new AdminException(messages.getMessage("admin.suppression.impossible"));
        } else {
            adminRepository.deleteById(id);
        }
    }

    @Override
    public AdminResponseDto patch(UUID id, AdminRequestDto requestDto) {
        Admin currentAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(ADMIN_NOT_FOUND)));

        updateGeneralUserInfo(requestDto, currentAdmin);

        Admin updated = adminRepository.save(currentAdmin);
        return adminMapper.toAdminResponseDto(updated);
    }

    private void verifyDto(AdminRequestDto requestDto) {
        if (requestDto == null)
            throw new AdminException(messages.getMessage("user.null"));
//        if (requestDto.firstName() == null || requestDto.firstName().isBlank())
//            throw new AdminException(messages.getMessage("user.firstname.null"));
//        if (requestDto.lastName() == null || requestDto.lastName().isBlank())
//            throw new AdminException(messages.getMessage("user.lastname.null"));
//        if (requestDto.email() == null ||requestDto.email().isBlank())
//            throw new AdminException(messages.getMessage("user.email.null"));
        // Vérification que l'email n'existe pas déjà
        if (adminRepository.existsByEmail(requestDto.email()))
            throw new AdminException(messages.getMessage("user.email.duplicate"));
//        if (requestDto.password() == null || requestDto.password().isBlank())
//            throw new AdminException(messages.getMessage("user.password.null"));
//        if (requestDto.position() == null ||requestDto.position().isBlank())
//            throw new AdminException(messages.getMessage("admin.position.null"));
    }

    private static void updateGeneralUserInfo(AdminRequestDto requestDto, Admin currentAdmin) {
        if (requestDto.lastName() != null && !requestDto.lastName().isBlank())
            currentAdmin.setLastName(requestDto.lastName());
        if (requestDto.firstName() != null && !requestDto.firstName().isBlank())
            currentAdmin.setFirstName(requestDto.firstName());
        if (requestDto.email() != null && !requestDto.email().isBlank())
            currentAdmin.setEmail(requestDto.email());
        if (requestDto.password() != null && !requestDto.password().isBlank())
            currentAdmin.setPassword(requestDto.password());
        if(requestDto.position() != null && !requestDto.position().isBlank())
            currentAdmin.setPosition(requestDto.position());
    }
}
