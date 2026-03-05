package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.VehicleException;
import com.accenture.rentalvehiclesapp.mapper.MotoMapper;
import com.accenture.rentalvehiclesapp.repository.entity.MotoRepository;
import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Motorcycle;
import com.accenture.rentalvehiclesapp.service.MotoService;
import com.accenture.rentalvehiclesapp.service.dto.patch.MotoPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.MotoRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.MotoResponseDto;
import com.accenture.rentalvehiclesapp.utils.Messages;
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
public class MotoServiceImpl implements MotoService {


    private final MotoRepository motoRepository;
    private final MessageSourceAccessor messages;
    private final MotoMapper motoMapper;

    @Override
    public MotoResponseDto save(MotoRequestDto requestDto) throws VehicleException {
        verifyDto(requestDto);

        Motorcycle newMoto = motoMapper.toEntity(requestDto);
        Motorcycle saved = motoRepository.save(newMoto);

        return motoMapper.toMotoResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MotoResponseDto> findAll() {
        return motoRepository.findAll().stream()
                .map(motoMapper::toMotoResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MotoResponseDto findById(UUID id) {
        Motorcycle motorcycle = motoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(Messages.MOTORCYCLE_NOT_FOUND)));
        return motoMapper.toMotoResponseDto(motorcycle);
    }

    @Override
    public boolean existsById(UUID id) {
        return motoRepository.existsById(id);
    }

    @Override
    public MotoResponseDto patch(UUID id, MotoPatchDto patchDto) {
        Motorcycle currentMoto = motoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(Messages.MOTORCYCLE_NOT_FOUND)));

        if (currentMoto.isRemovedFromPark())
            throw new VehicleException(messages.getMessage("vehicle.already.removed"));

        updateGeneralVehicleInfo(patchDto, currentMoto);

        updatePoweredTwoWheelerInfo(patchDto, currentMoto);

        updateMotoInfo(patchDto, currentMoto);

        Motorcycle updated = motoRepository.save(currentMoto);

        return motoMapper.toMotoResponseDto(updated);
    }

    @Override
    public void delete(UUID id) {
        Motorcycle motorcycle = motoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(Messages.MOTORCYCLE_NOT_FOUND)));

        if (motorcycle.isRemovedFromPark()) {
            throw new VehicleException(messages.getMessage("vehicle.already.removed"));
        }

        if (motorcycle.isActive()) {
            motorcycle.setRemovedFromPark(true);
            motorcycle.setActive(false);
            motoMapper.toMotoPatchDto(motorcycle);
        } else {
            motoRepository.delete(motorcycle);
        }
    }
    private void updateMotoInfo(MotoPatchDto patchDto, Motorcycle currentMoto) {

        if (patchDto.cylinders() != null && patchDto.cylinders() >= 1 && patchDto.cylinders() <= 6)
            currentMoto.setCylinders(patchDto.cylinders());
        if (patchDto.displacement() != null && patchDto.displacement() >= 125)
            currentMoto.setDisplacement(patchDto.displacement());
        if (patchDto.weight() != null && patchDto.weight() >= 1)
            currentMoto.setWeight(patchDto.weight());
        if (patchDto.power() != null && patchDto.power() >= 7 && patchDto.power() <= 220)
            currentMoto.setPower(patchDto.power());
        if(patchDto.seatHeight() != null && patchDto.seatHeight()>= 650  && patchDto.seatHeight() <= 950)
            currentMoto.setSeatHeight(patchDto.seatHeight());
        if (patchDto.category() != null)
            currentMoto.setCategory(patchDto.category());
    }

    private void updatePoweredTwoWheelerInfo(MotoPatchDto patchDto, Motorcycle currentMoto) {
        if (patchDto.transmission() != null)
            currentMoto.setTransmission(patchDto.transmission());
    }

    private void updateGeneralVehicleInfo(MotoPatchDto patchDto, Motorcycle currentMoto) {
        if (patchDto.brand() != null && !patchDto.brand().isBlank())
            currentMoto.setBrand(patchDto.brand());
        if (patchDto.model() != null && !patchDto.model().isBlank())
            currentMoto.setModel(patchDto.model());
        if (patchDto.color() != null && !patchDto.color().isBlank())
            currentMoto.setColor(patchDto.color());
        if (patchDto.basicDailyRate() != null && patchDto.basicDailyRate() > 0)
            currentMoto.setBasicDailyRate(patchDto.basicDailyRate());
        if (patchDto.mileage() != null && patchDto.mileage() >= 0)
            currentMoto.setMileage(patchDto.mileage());
        if (patchDto.active() != null)
            currentMoto.setActive(patchDto.active());
        if (patchDto.removedFromPark() != null)
            currentMoto.setRemovedFromPark(patchDto.removedFromPark());
    }


    private void verifyDto(MotoRequestDto requestDto) {
        if (requestDto == null)
            throw new VehicleException(messages.getMessage("vehicle.null"));
    }
}
