package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.LicenceException;
import com.accenture.rentalvehiclesapp.mapper.LicenceMapper;
import com.accenture.rentalvehiclesapp.repository.entity.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.LicenceRepository;
import com.accenture.rentalvehiclesapp.service.LicenceService;
import com.accenture.rentalvehiclesapp.service.dto.LicenceRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.LicenceResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class LicenceServiceImpl implements LicenceService {
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

    private void check(LicenceRequestDto requestDto) {
    }
}
