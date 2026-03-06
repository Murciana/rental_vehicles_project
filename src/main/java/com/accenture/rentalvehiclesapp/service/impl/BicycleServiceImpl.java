package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.VehicleException;
import com.accenture.rentalvehiclesapp.mapper.BicycleMapper;
import com.accenture.rentalvehiclesapp.repository.entity.BicycleRepository;
import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Bicycle;
import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Car;
import com.accenture.rentalvehiclesapp.service.BicycleService;
import com.accenture.rentalvehiclesapp.service.dto.patch.BicyclePatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.BicycleRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.BicycleResponseDto;
import com.accenture.rentalvehiclesapp.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class BicycleServiceImpl implements BicycleService {

    private final BicycleRepository bicycleRepository;
    private final MessageSourceAccessor messages;
    private final BicycleMapper bicycleMapper;

    @Override
    public BicycleResponseDto save(BicycleRequestDto requestDto) throws VehicleException {
        verifyDto(requestDto);

        Bicycle newBicycle = bicycleMapper.toEntity(requestDto);
        Bicycle saved = bicycleRepository.save(newBicycle);

        return bicycleMapper.toBicycleResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BicycleResponseDto> findAll() {
        return bicycleRepository.findAll().stream()
                .map(bicycleMapper::toBicycleResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BicycleResponseDto findById(UUID id) {
        Bicycle bicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.BICYCLE_NOT_FOUND));
        return bicycleMapper.toBicycleResponseDto(bicycle);
    }

    @Override
    public boolean existsById(UUID id) {
        return bicycleRepository.existsById(id);
    }


    @Override
    public BicycleResponseDto patch(UUID id, BicyclePatchDto patchDto) {
        Bicycle currentBicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.BICYCLE_NOT_FOUND));

        if (currentBicycle.isRemovedFromPark())
            throw new VehicleException(Messages.VEHICULE_ALREADY_REMOVED);

        updateGeneralVehicleInfo(patchDto, currentBicycle);

        updateBicycleInfo(patchDto, currentBicycle);

        Bicycle updated = bicycleRepository.save(currentBicycle);

        return bicycleMapper.toBicycleResponseDto(updated);
    }


    @Override
    public void delete(UUID id) {
        Bicycle bicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.BICYCLE_NOT_FOUND));

        if (bicycle.isRemovedFromPark()){
            throw new VehicleException(Messages.VEHICULE_ALREADY_REMOVED);
        }

        if (bicycle.isActive()) {
            bicycle.setRemovedFromPark(true);
            bicycle.setActive(false);
            bicycleMapper.toBicyclePatchDto(bicycle);
        } else {
            bicycleRepository.delete(bicycle);
        }
    }

    private void verifyDto(BicycleRequestDto requestDto) {
        if (requestDto == null)
            throw new VehicleException(Messages.VEHICULE_NULL);
        if (Boolean.TRUE.equals(requestDto.electric()) && requestDto.autonomy() == null)
                throw new VehicleException(Messages.BICYCLE_AUTONOMY_NULL);
        if (Boolean.TRUE.equals(requestDto.electric()) && requestDto.batteryCapacity() == null)
                throw new VehicleException(Messages.BICYCLE_BATTERY_CAPACITY_NULL);
    }

    private void updateBicycleInfo(BicyclePatchDto patchDto, Bicycle currentBicycle) {
        if (patchDto.frameSize() != null && patchDto.frameSize() >= 1)
            currentBicycle.setFrameSize(patchDto.frameSize());
        if(patchDto.discBrakes() != null)
            currentBicycle.setDiscBrakes(patchDto.discBrakes());
        if (patchDto.weight() != null && patchDto.weight() >= 1)
            currentBicycle.setWeight(patchDto.weight());
        if (patchDto.electric() != null)
            currentBicycle.setElectric(patchDto.electric());

        if (Boolean.TRUE.equals(patchDto.electric())){
            if (patchDto.autonomy() != null && patchDto.autonomy() >= 0)
                currentBicycle.setAutonomy(patchDto.autonomy());
            if (patchDto.batteryCapacity() != null && patchDto.batteryCapacity() >= 0)
                currentBicycle.setBatteryCapacity(patchDto.batteryCapacity());
        }
        if (patchDto.category() != null)
            currentBicycle.setCategory(patchDto.category());
    }

    private void updateGeneralVehicleInfo(BicyclePatchDto patchDto, Bicycle currentBicycle) {
        if (patchDto.brand() != null && !patchDto.brand().isBlank())
            currentBicycle.setBrand(patchDto.brand());
        if (patchDto.model() != null && !patchDto.model().isBlank())
            currentBicycle.setModel(patchDto.model());
        if (patchDto.color() != null && !patchDto.color().isBlank())
            currentBicycle.setColor(patchDto.color());
        if (patchDto.basicDailyRate() != null && patchDto.basicDailyRate() > 0)
            currentBicycle.setBasicDailyRate(patchDto.basicDailyRate());
        if (patchDto.mileage() != null && patchDto.mileage() >= 0)
            currentBicycle.setMileage(patchDto.mileage());
        if (patchDto.active() != null)
            currentBicycle.setActive(patchDto.active());
        if (patchDto.removedFromPark() != null)
            currentBicycle.setRemovedFromPark(patchDto.removedFromPark());
    }
}
