package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.mapper.CustomerMapper;
import com.accenture.rentalvehiclesapp.repository.CustomerRepository;
import com.accenture.rentalvehiclesapp.repository.entity.LicenceRepository;
import com.accenture.rentalvehiclesapp.service.fake.FakeCustomerMapper;
import com.accenture.rentalvehiclesapp.service.fake.FakeCustomerRepository;
import com.accenture.rentalvehiclesapp.service.fake.FakeLicenceRepository;
import com.accenture.rentalvehiclesapp.service.fake.FakePasswordEncoder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

    private FakeCustomerRepository customerRepository;
    private FakeLicenceRepository licenceRepository;
    private FakeCustomerMapper customerMapper;
    private FakePasswordEncoder passwordEncoder;


}