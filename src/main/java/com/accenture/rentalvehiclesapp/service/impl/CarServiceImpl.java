package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.AdminException;
import com.accenture.rentalvehiclesapp.exception.VehicleException;
import com.accenture.rentalvehiclesapp.mapper.CarMapper;
import com.accenture.rentalvehiclesapp.repository.entity.CarRepository;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Admin;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Customer;
import com.accenture.rentalvehiclesapp.repository.entity.vehicle.Car;
import com.accenture.rentalvehiclesapp.service.CarService;
import com.accenture.rentalvehiclesapp.service.dto.CarPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.CarRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.CarResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.smartcardio.CardException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private static final String CAR_NOT_FOUND = "car.id.notfound";
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
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(CAR_NOT_FOUND)));
        return carMapper.toCarResponseDto(car);
    }

    @Override
    public CarResponseDto patch(UUID id, CarPatchDto patchDto) {
        Car currentCar = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(CAR_NOT_FOUND)));

        updateGeneralVehicleInfo(patchDto, currentCar);

        updateFourWheeledInfo(patchDto, currentCar);

        updateCarInfo(patchDto, currentCar);

        Car updated = carRepository.save(currentCar);

        return carMapper.toCarResponseDto(updated);
    }


    @Override
    public void delete(UUID id) {
        if (!carRepository.existsById(id))
            throw new EntityNotFoundException(messages.getMessage(CAR_NOT_FOUND));
        carRepository.deleteById(id);
    }

    private void verifyDto(CarRequestDto requestDto) {
        if (requestDto == null)
            throw new VehicleException(messages.getMessage("vehicle.null"));
//        if(requestDto.brand() == null ||requestDto.brand().isBlank())
//            throw new VehicleException(messages.getMessage());
//        if(requestDto.model() ==null || requestDto.model().isBlank())
//            throw new VehicleException(messages.getMessage());
//        if(requestDto.color() == null || requestDto.color().isBlank())
//            throw new VehicleException(messages.getMessage());
//        if(requestDto.basicDailyRate() <1)
//            throw new VehicleException(messages.getMessage());
//        if(requestDto.mileage() <0)
//            throw new VehicleException(messages.getMessage());
//        if(requestDto.seats() <1)
//            throw new VehicleException(messages.getMessage());
//        if (requestDto.fuel() == null)
//            throw new VehicleException(messages.getMessage());
//        if(requestDto.transmission() == null)
//            throw new VehicleException(messages.getMessage());
//        if(requestDto.airConditioning() == null)
//            throw new VehicleException(messages.getMessage());
//        if(requestDto.doors() <2)
//            throw new VehicleException(messages.getMessage());
//        if(requestDto.luggageCapacity() <0)
//            throw new VehicleException(messages.getMessage());
    }

    private void updateCarInfo(CarPatchDto patchDto, Car currentCar) {
        if (patchDto.doors() != null && patchDto.doors() >= 2)
            currentCar.setDoors(patchDto.doors());
        if (patchDto.luggageCapacity() != null && patchDto.luggageCapacity() >= 0)
            currentCar.setLuggageCapacity(patchDto.luggageCapacity());
    }

    private void updateFourWheeledInfo(CarPatchDto patchDto, Car currentCar) {
        if (patchDto.seats() != null && patchDto.seats() >= 1)
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
    }
}
