package com.accenture.rentalvehiclesapp.service.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddressDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAddressDtoOK() {

        AddressDto addressDto = new AddressDto("1 rue Principale", "29200", "Brest");
        Assertions.assertAll(
                () -> Assertions.assertNotNull(addressDto.street()),
                () -> Assertions.assertFalse(addressDto.street().isBlank()),
                () -> Assertions.assertNotNull(addressDto.postCode()),
                () -> Assertions.assertFalse(addressDto.postCode().isBlank()),
                () -> Assertions.assertFalse(addressDto.postCode().isBlank()),
                () -> Assertions.assertTrue(addressDto.postCode().matches("^\\d{5}$")),
                () -> Assertions.assertNotNull(addressDto.city()),
                () -> Assertions.assertFalse(addressDto.city().isBlank())
        );
    }

    @Test
    public void testPDtoStreetNull() {
        AddressDto addressDto = new AddressDto(null, "29200", "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.street.null")))
        );
    }

    @Test
    public void testPDtoStreetBlank() {
        AddressDto addressDto = new AddressDto(" ", "29200", "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.street.null")))
        );
    }

    @Test
    public void testPDtoPostCodeNull() {
        AddressDto addressDto = new AddressDto("1 rue Principale", null, "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.postcode.null")))
        );
    }

    @Test
    public void testPDtoPostCodeBlank() {
        AddressDto addressDto = new AddressDto("1 rue Principale", " ", "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.postcode.null")))
        );
    }

    @Test
    public void testPDtoPostCodeInvalidPattern() {
        AddressDto addressDto = new AddressDto("1 rue Principale", "292000", "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.postcode.invalid")))
        );
    }

    @Test
    public void testPDtoPostCityNull() {
        AddressDto addressDto = new AddressDto("1 rue Principale", "29200", null);

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.city.null")))
        );
    }

    @Test
    public void testPDtoPostCityBlank() {
        AddressDto addressDto = new AddressDto("1 rue Principale", "29200", " ");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(addressDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.city.null")))
        );
    }
}