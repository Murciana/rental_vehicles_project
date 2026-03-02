package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.AdminException;
import com.accenture.rentalvehiclesapp.exception.LicenceException;
import com.accenture.rentalvehiclesapp.mapper.LicenceMapper;
import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.LicenceRepository;
import com.accenture.rentalvehiclesapp.service.LicenceService;
import com.accenture.rentalvehiclesapp.service.dto.LicenceRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.LicenceResponseDto;
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
public class LicenceServiceImpl implements LicenceService {
    private static final String LICENCE_NOT_FOUND = "licence.id.notfound";
    private final LicenceRepository licenceRepository;
    private final MessageSourceAccessor messages;
    private final LicenceMapper licenceMapper;

    @Override
    public LicenceResponseDto save(LicenceRequestDto requestDto) throws LicenceException {
        check(requestDto);

        Licence newLicence = licenceMapper.toEntity(requestDto);
        Licence saved = licenceRepository.save(newLicence);

        return licenceMapper.toLicenceResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenceResponseDto> findAll() {
        return licenceRepository.findAll().stream()
                .map(licenceMapper::toLicenceResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LicenceResponseDto findById(UUID id) {
        Licence licence = licenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(LICENCE_NOT_FOUND)));
        return licenceMapper.toLicenceResponseDto(licence);    }


    private void check(LicenceRequestDto requestDto) {
        if (requestDto == null)
            throw new AdminException(messages.getMessage("licence.null"));
        if (requestDto.name() == null)
            throw new AdminException(messages.getMessage("licence.name.null"));
    }
}
