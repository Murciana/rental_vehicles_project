package com.accenture.rentalvehiclesapp.service.fake;

import com.accenture.rentalvehiclesapp.mapper.CustomerMapper;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Address;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Customer;
import com.accenture.rentalvehiclesapp.service.dto.AddressDto;
import com.accenture.rentalvehiclesapp.service.dto.request.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CustomerResponseDto;
import com.accenture.rentalvehiclesapp.service.dto.response.LicenceResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakeCustomerMapper implements CustomerMapper {

    private final UUID uuid = UUID.randomUUID();

    @Override
    public Customer toEntity(CustomerRequestDto requestDto) {
        Address fakeAddress = new Address();
        fakeAddress.setStreet(requestDto.address().street());
        fakeAddress.setPostCode(requestDto.address().postCode());
        fakeAddress.setCity(requestDto.address().city());

        Customer fakeCustomer = new Customer();
        fakeCustomer.setId(uuid);
        fakeCustomer.setLastName(requestDto.lastName());
        fakeCustomer.setFirstName(requestDto.firstName());
        fakeCustomer.setEmail(requestDto.email());
        fakeCustomer.setPassword(requestDto.password());
        fakeCustomer.setBirthDate(requestDto.birthDate());
        fakeCustomer.setAddress(fakeAddress);

        return fakeCustomer;
    }

    @Override
    public CustomerResponseDto toCustomerResponseDto(Customer fakeCustomer) {
        AddressDto fakeAddress = new AddressDto(
                fakeCustomer.getAddress().getStreet(),
                fakeCustomer.getAddress().getPostCode(),
                fakeCustomer.getAddress().getCity()
                );

        List<LicenceResponseDto> fakeLicences = fakeCustomer.getLicences().stream()
                .map(licence -> new LicenceResponseDto(licence.getId(), licence.getName()))
                .toList();

        return new CustomerResponseDto(
                fakeCustomer.getId(),
                fakeCustomer.getLastName(),
                fakeCustomer.getFirstName(),
                fakeCustomer.getEmail(),
                fakeCustomer.getBirthDate(),
                fakeCustomer.getRegistrationDate(),
                fakeAddress,
                fakeLicences
        );
    }
}
