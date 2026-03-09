package com.accenture.rentalvehiclesapp.service.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

@DisplayName("Address dto tests")
class AddressDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("address dto OK")
    void testAddressDtoOK() {

        AddressDto dto = new AddressDto("1 rue Principale", "29200", "Brest");
        Assertions.assertAll(
                () -> Assertions.assertNotNull(dto.street()),
                () -> Assertions.assertFalse(dto.street().isBlank()),
                () -> Assertions.assertNotNull(dto.postCode()),
                () -> Assertions.assertFalse(dto.postCode().isBlank()),
                () -> Assertions.assertFalse(dto.postCode().isBlank()),
                () -> Assertions.assertTrue(dto.postCode().matches("^\\d{5}$")),
                () -> Assertions.assertNotNull(dto.city()),
                () -> Assertions.assertFalse(dto.city().isBlank())
        );
    }

    @Test
    @DisplayName("invalid : street null")
    void testPDtoStreetNull() {
        AddressDto dto = new AddressDto(null, "29200", "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.street.null")))
        );
    }

    @Test
    @DisplayName("invalid : street blank")
    void testPDtoStreetBlank() {
        AddressDto dto = new AddressDto(" ", "29200", "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.street.null")))
        );
    }

    @Test
    @DisplayName("invalid : post code null")
    void testPDtoPostCodeNull() {
        AddressDto dto = new AddressDto("1 rue Principale", null, "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.postcode.null")))
        );
    }

    @Test
    @DisplayName("invalid : post code blank")
    void testPDtoPostCodeBlank() {
        AddressDto dto = new AddressDto("1 rue Principale", " ", "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.postcode.null")))
        );
    }

    @Test
    @DisplayName("invalid : invalid post code pattern")
    void testPDtoPostCodeInvalidPattern() {
        AddressDto dto = new AddressDto("1 rue Principale", "292000", "Brest");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.postcode.invalid")))
        );
    }

    @Test
    @DisplayName("invalid : city null")
    void testPDtoPostCityNull() {
        AddressDto dto = new AddressDto("1 rue Principale", "29200", null);

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.city.null")))
        );
    }

    @Test
    @DisplayName("invalid : city blank")
    void testPDtoPostCityBlank() {
        AddressDto dto = new AddressDto("1 rue Principale", "29200", " ");

        Set<ConstraintViolation<AddressDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("address.city.null")))
        );
    }
}