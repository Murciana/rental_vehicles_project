package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.CustomerException;
import com.accenture.rentalvehiclesapp.mapper.CustomerMapper;
import com.accenture.rentalvehiclesapp.repository.CustomerRepository;
import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.LicenceRepository;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Customer;
import com.accenture.rentalvehiclesapp.service.CustomerService;
import com.accenture.rentalvehiclesapp.service.dto.request.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CustomerResponseDto;
import com.accenture.rentalvehiclesapp.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
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
    private final MessageSourceAccessor messages;
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
    public CustomerResponseDto patch(UUID id, CustomerRequestDto requestDto) {
        Customer currentCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Messages.CUSTOMER_NOT_FOUND));

        updateGeneralUserInfo(requestDto, currentCustomer);

        updateCustomerInfo(requestDto, currentCustomer);

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

    private static void updateGeneralUserInfo(CustomerRequestDto requestDto, Customer currentCustomer) {
        if (requestDto.lastName() != null && !requestDto.lastName().isBlank())
            currentCustomer.setLastName(requestDto.lastName());
        if (requestDto.firstName() != null && !requestDto.firstName().isBlank())
            currentCustomer.setFirstName(requestDto.firstName());
        if (requestDto.email() != null && !requestDto.email().isBlank())
            currentCustomer.setEmail(requestDto.email());
        if (requestDto.password() != null && !requestDto.password().isBlank())
            currentCustomer.setPassword(requestDto.password());
    }

    private void updateCustomerInfo(CustomerRequestDto requestDto, Customer currentCustomer) {
        if (requestDto.birthDate() != null)
            currentCustomer.setBirthDate(requestDto.birthDate());

        if (requestDto.address() != null) {
            if (requestDto.address().street() != null && !requestDto.address().street().isBlank()) {
                currentCustomer.getAddress().setStreet(requestDto.address().street());
            }
            if (requestDto.address().postCode() != null && !requestDto.address().postCode().isBlank()) {
                currentCustomer.getAddress().setPostCode(requestDto.address().postCode());
            }
            if (requestDto.address().city() != null && !requestDto.address().city().isBlank()) {
                currentCustomer.getAddress().setCity(requestDto.address().city());
            }
        }

        if (requestDto.licencesId() != null && !requestDto.licencesId().isEmpty()) {
            List<Licence> licences = licenceRepository.findAllById(requestDto.licencesId());
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
