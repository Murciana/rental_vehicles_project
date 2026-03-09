package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.CustomerException;
import com.accenture.rentalvehiclesapp.mapper.CustomerMapper;
import com.accenture.rentalvehiclesapp.repository.CustomerRepository;
import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.LicenceRepository;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Customer;
import com.accenture.rentalvehiclesapp.service.CustomerService;
import com.accenture.rentalvehiclesapp.service.dto.patch.CustomerPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CustomerResponseDto;
import com.accenture.rentalvehiclesapp.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final LicenceRepository licenceRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerResponseDto save(CustomerRequestDto requestDto) throws CustomerException {
        verifyDto(requestDto);

        Customer newCustomer = customerMapper.toEntity(requestDto);
        newCustomer.setPassword(passwordEncoder.encode(requestDto.password()));

        if (requestDto.licencesId() != null && !requestDto.licencesId().isEmpty()) {
            List<Licence> licences = licenceRepository.findAllById(requestDto.licencesId());
            newCustomer.setLicences(licences);
        }

        Customer saved = customerRepository.save(newCustomer);

        return customerMapper.toCustomerResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> findAll() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto findById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.CUSTOMER_NOT_FOUND));
        return customerMapper.toCustomerResponseDto(customer);
    }

    @Override
    public void delete(UUID id) {
        if (!customerRepository.existsById(id))
            throw new EntityNotFoundException(Messages.CUSTOMER_NOT_FOUND);

        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponseDto patch(UUID id, CustomerPatchDto patchDto) {
        Customer currentCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.CUSTOMER_NOT_FOUND));

        updateGeneralUserInfo(patchDto, currentCustomer);

        updateCustomerInfo(patchDto, currentCustomer);

        Customer updated = customerRepository.save(currentCustomer);

        return customerMapper.toCustomerResponseDto(updated);
    }

    private void verifyDto(CustomerRequestDto requestDto) {
        if (requestDto == null)
            throw new CustomerException(Messages.USER_NULL);
        if (customerRepository.existsByEmail(requestDto.email()))
            throw new CustomerException(Messages.USER_EMAIL_DUPLICATE);
        if (Period.between(requestDto.birthDate(), LocalDate.now()).getYears() < 18)
            throw new CustomerException(Messages.CUSTOMER_UNDERAGE);
    }

    private static void updateGeneralUserInfo(CustomerPatchDto patchDto, Customer currentCustomer) {
        if (patchDto.lastName() != null && !patchDto.lastName().isBlank())
            currentCustomer.setLastName(patchDto.lastName());
        if (patchDto.firstName() != null && !patchDto.firstName().isBlank())
            currentCustomer.setFirstName(patchDto.firstName());
        if (patchDto.email() != null && !patchDto.email().isBlank())
            currentCustomer.setEmail(patchDto.email());
        if (patchDto.password() != null && !patchDto.password().isBlank())
            currentCustomer.setPassword(patchDto.password());
    }

    private void updateCustomerInfo(CustomerPatchDto patchDto, Customer currentCustomer) {
        if (patchDto.birthDate() != null)
            currentCustomer.setBirthDate(patchDto.birthDate());

        if (patchDto.address() != null) {
            if (patchDto.address().street() != null && !patchDto.address().street().isBlank()) {
                currentCustomer.getAddress().setStreet(patchDto.address().street());
            }
            if (patchDto.address().postCode() != null && !patchDto.address().postCode().isBlank()) {
                currentCustomer.getAddress().setPostCode(patchDto.address().postCode());
            }
            if (patchDto.address().city() != null && !patchDto.address().city().isBlank()) {
                currentCustomer.getAddress().setCity(patchDto.address().city());
            }
        }

        if (patchDto.licencesId() != null && !patchDto.licencesId().isEmpty()) {
            List<Licence> licences = licenceRepository.findAllById(patchDto.licencesId());
            currentCustomer.setLicences(licences);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto findByEmail(String email) {
        Customer found = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(Messages.CUSTOMER_NOT_FOUND));

        return customerMapper.toCustomerResponseDto(found);
    }
}
