package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.VehicleException;
import com.accenture.rentalvehiclesapp.mapper.CarMapper;
import com.accenture.rentalvehiclesapp.repository.entity.CarRepository;
import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Car;
import com.accenture.rentalvehiclesapp.service.CarService;
import com.accenture.rentalvehiclesapp.service.dto.patch.CarPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.CarRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CarResponseDto;
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
public class CarServiceImpl implements CarService {


    private final CarRepository carRepository;
    private final MessageSourceAccessor messages;
    private final CarMapper carMapper;

    @Override
    public CarResponseDto save(CarRequestDto requestDto) throws VehicleException {
        verifyDto(requestDto);

        Car newCar = carMapper.toEntity(requestDto);
        Car saved = carRepository.save(newCar);

        return carMapper.toCarResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDto> findAll() {
        return carRepository.findAll().stream()
                .map(carMapper::toCarResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDto findById(UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.CAR_NOT_FOUND));
        return carMapper.toCarResponseDto(car);
    }

    @Override
    public boolean existsById(UUID id) {
        return carRepository.existsById(id);
    }


    @Override
    public CarResponseDto patch(UUID id, CarPatchDto patchDto) {
        Car currentCar = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.CAR_NOT_FOUND));

        if (currentCar.isRemovedFromPark())
            throw new VehicleException(Messages.VEHICULE_ALREADY_REMOVED);

        updateGeneralVehicleInfo(patchDto, currentCar);

        updateFourWheeledInfo(patchDto, currentCar);

        updateCarInfo(patchDto, currentCar);

        Car updated = carRepository.save(currentCar);

        return carMapper.toCarResponseDto(updated);
    }


    @Override
    public void delete(UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.CAR_NOT_FOUND));

        if (car.isRemovedFromPark()){
            throw new VehicleException(Messages.VEHICULE_ALREADY_REMOVED);
        }

        if (car.isActive()) {
            car.setRemovedFromPark(true);
            car.setActive(false);
            carMapper.toCarPatchDto(car);
        } else {
            carRepository.delete(car);
        }
    }

    private void verifyDto(CarRequestDto requestDto) {
        if (requestDto == null)
            throw new VehicleException(Messages.VEHICULE_NULL);
        if (requestDto.doors() != 3 && requestDto.doors() != 5)
            throw new VehicleException(Messages.CAR_DOORS_INVALID);
    }

    private void updateCarInfo(CarPatchDto patchDto, Car currentCar) {
        if (patchDto.doors() != null && patchDto.doors() == 3 || patchDto.doors() != null && patchDto.doors() == 5)
            currentCar.setDoors(patchDto.doors());
        if (patchDto.luggageCapacity() != null && patchDto.luggageCapacity() >= 0)
            currentCar.setLuggageCapacity(patchDto.luggageCapacity());
        if (patchDto.category() != null)
            currentCar.setCategory(patchDto.category());
    }

    private void updateFourWheeledInfo(CarPatchDto patchDto, Car currentCar) {
        if (patchDto.seats() != null && patchDto.seats() >= 2)
            currentCar.setSeats(patchDto.seats());
        if (patchDto.fuel() != null)
            currentCar.setFuel(patchDto.fuel());
        if (patchDto.transmission() != null)
            currentCar.setTransmission(patchDto.transmission());
        if (patchDto.airConditioning() != null)
            currentCar.setAirConditioning(patchDto.airConditioning());
    }

    private void updateGeneralVehicleInfo(CarPatchDto patchDto, Car currentCar) {
        if (patchDto.brand() != null && !patchDto.brand().isBlank())
            currentCar.setBrand(patchDto.brand());
        if (patchDto.model() != null && !patchDto.model().isBlank())
            currentCar.setModel(patchDto.model());
        if (patchDto.color() != null && !patchDto.color().isBlank())
            currentCar.setColor(patchDto.color());
        if (patchDto.basicDailyRate() != null && patchDto.basicDailyRate() > 0)
            currentCar.setBasicDailyRate(patchDto.basicDailyRate());
        if (patchDto.mileage() != null && patchDto.mileage() >= 0)
            currentCar.setMileage(patchDto.mileage());
        if (patchDto.active() != null)
            currentCar.setActive(patchDto.active());
        if (patchDto.removedFromPark() != null)
            currentCar.setRemovedFromPark(patchDto.removedFromPark());
    }
}
