package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.CustomerException;
import com.accenture.rentalvehiclesapp.mapper.CustomerMapper;
import com.accenture.rentalvehiclesapp.repository.entity.CustomerRepository;
import com.accenture.rentalvehiclesapp.repository.entity.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.LicenceRepository;
import com.accenture.rentalvehiclesapp.repository.entity.loggedInUser.Customer;
import com.accenture.rentalvehiclesapp.service.CustomerService;
import com.accenture.rentalvehiclesapp.service.dto.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.CustomerResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
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
    private static final String CUSTOMER_NOT_FOUND = "customer.id.notfound";
    private static final String LICENCES_NOT_FOUND = "licence.id.notfound";


    private final CustomerRepository customerRepository;
    private final LicenceRepository licenceRepository;
    private final MessageSourceAccessor messages;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDto save(CustomerRequestDto requestDto) throws CustomerException {
        check(requestDto);

        Customer newCustomer = customerMapper.ToEntity(requestDto);

        // on récupère les permis avec leurs
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
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(CUSTOMER_NOT_FOUND)));
        return customerMapper.toCustomerResponseDto(customer);
    }

    @Override
    public void delete(UUID id) {
        if (!customerRepository.existsById(id))
            throw new EntityNotFoundException(messages.getMessage(CUSTOMER_NOT_FOUND));
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponseDto patch(UUID id, CustomerRequestDto requestDto) {
        Customer currentCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getMessage(CUSTOMER_NOT_FOUND)));
        if (requestDto.lastName() != null && !requestDto.lastName().isBlank())
            currentCustomer.setLastName(requestDto.lastName());
        if (requestDto.firstName() != null && !requestDto.firstName().isBlank())
            currentCustomer.setFirstName(requestDto.firstName());
        if (requestDto.email() != null && !requestDto.email().isBlank())
            currentCustomer.setEmail(requestDto.email());
        if (requestDto.password() != null && !requestDto.password().isBlank())
            currentCustomer.setPassword(requestDto.password());
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

        Customer updated = customerRepository.save(currentCustomer);
        return customerMapper.toCustomerResponseDto(updated);
    }

    private void check(CustomerRequestDto requestDto) {
        if (requestDto == null)
            throw new CustomerException(messages.getMessage("user.null"));
        if (requestDto.firstName() == null)
            throw new CustomerException(messages.getMessage("user.firstname.null"));
        if (requestDto.lastName() == null)
            throw new CustomerException(messages.getMessage("user.lastname.null"));
        if (requestDto.email() == null)
            throw new CustomerException(messages.getMessage("user.email.null"));
        // Vérification que l'email n'existe pas déjà
        if (customerRepository.existsByEmail(requestDto.email()))
            throw new CustomerException(messages.getMessage("user.email.duplicate"));
        if (requestDto.password() == null)
            throw new CustomerException(messages.getMessage("user.password.null"));
        if (requestDto.birthDate() == null)
            throw new CustomerException(messages.getMessage("customer.birthdate.null"));
        // Vérification de la majorité
        if (Period.between(requestDto.birthDate(), LocalDate.now()).getYears() < 18)
            throw new CustomerException(messages.getMessage("customer.birthdate.underage"));
        if (requestDto.address() == null)
            throw new CustomerException(messages.getMessage("customer.address.null"));
    }
}
