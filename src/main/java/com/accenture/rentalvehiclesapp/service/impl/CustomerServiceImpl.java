package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.CustomerException;
import com.accenture.rentalvehiclesapp.mapper.CustomerMapper;
import com.accenture.rentalvehiclesapp.repository.entity.CustomerRepository;
import com.accenture.rentalvehiclesapp.repository.entity.loggedInUser.Customer;
import com.accenture.rentalvehiclesapp.service.CustomerService;
import com.accenture.rentalvehiclesapp.service.dto.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.CustomerResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@AllArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final MessageSourceAccessor messages;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDto save(CustomerRequestDto requestDto) throws CustomerException {
        check(requestDto);

        Customer newCustomer = customerMapper.ToEntity(requestDto);
        Customer saved = customerRepository.save(newCustomer);

        return customerMapper.toCustomerResponseDto(saved);
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
