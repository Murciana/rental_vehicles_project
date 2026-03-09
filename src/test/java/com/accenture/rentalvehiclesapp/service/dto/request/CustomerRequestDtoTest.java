package com.accenture.rentalvehiclesapp.service.dto.request;

import com.accenture.rentalvehiclesapp.service.dto.AddressDto;
import com.accenture.rentalvehiclesapp.utils.Messages;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@DisplayName("Customer request dto tests")
class CustomerRequestDtoTest {

    private Validator validator;


    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("request dto OK")
    void testCustomerDtoOK() {

        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(dto.lastName()),
                () -> Assertions.assertFalse(dto.lastName().isBlank()),
                () -> Assertions.assertNotNull(dto.firstName()),
                () -> Assertions.assertFalse(dto.firstName().isBlank()),
                () -> Assertions.assertNotNull(dto.email()),
                () -> Assertions.assertFalse(dto.email().isBlank()),
                () -> Assertions.assertTrue(dto.email().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")),
                () -> Assertions.assertNotNull(dto.password()),
                () -> Assertions.assertFalse(dto.password().isBlank()),
                () -> Assertions.assertTrue(dto.password().length() >= 8),
                () -> Assertions.assertTrue(dto.password().length() <= 16),
                () -> Assertions.assertTrue(dto.password().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#_&§-]).{8,16}$")),
                () -> Assertions.assertNotNull(dto.birthDate()),
                () -> Assertions.assertTrue(dto.birthDate().isBefore(LocalDate.now())),
                () -> Assertions.assertNotNull(dto.address()),
                () -> Assertions.assertNotNull(dto.licencesId()),
                () -> Assertions.assertFalse(dto.licencesId().isEmpty()),
                () -> Assertions.assertEquals(2, dto.licencesId().size())
        );
    }

    @Test
    @DisplayName("invalid: last name null")
    void testCustomerDtoLastNameNull() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                null,
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_LAST_NAME_NULL)))
        );
    }

    @Test
    @DisplayName("invalid: last name blank")
    void testCustomerDtoLastNameBlank() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                " ",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_LAST_NAME_NULL)))
        );
    }

    @Test
    @DisplayName("invalid: first name null")
    void testCustomerDtoFirstNameNull() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                null,
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_FIRST_NAME_NULL)))
        );
    }

    @Test
    @DisplayName("invalid: first name blank")
    void testCustomerDtoFirstNameBlank() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                " ",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_FIRST_NAME_NULL)))
        );
    }

    @Test
    @DisplayName("invalid: email null")
    void testCustomerDtoEmailNull() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                null,
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_EMAIL_NULL)))
        );
    }

    @Test
    @DisplayName("invalid: email blank")
    void testCustomerDtoEmailBlank() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                " ",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_EMAIL_NULL)))
        );
    }

    @Test
    @DisplayName("invalid: email pattern not respected")
    void testCustomerDtoEmailInvalid() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe-email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_EMAIL_PATTERN_INVALID)))
        );
    }

    @Test
    @DisplayName("invalid: password null")
    void testCustomerDtoPasswordNull() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                null,
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_PASSWORD_NULL)))
        );
    }

    @Test
    @DisplayName("invalid: password blank")
    void testCustomerDtoPasswordBlank() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                " ",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_PASSWORD_NULL)))
        );
    }

    @Test
    @DisplayName("invalid: password with with invalid characters")
    void testCustomerDtoPasswordInvalidCharacters() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "'/|1GGAi",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_PASSWORD_PATTERN_INVALID)))
        );
    }

    @Test
    @DisplayName("invalid: password too short")
    void testCustomerDtoPasswordTooShort() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygG",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_PASSWORD_SIZE)))
        );
    }

    @Test
    @DisplayName("invalid: password too long")
    void testCustomerDtoPasswordTooLong() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG8798&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.USER_PASSWORD_SIZE)))
        );
    }

    @Test
    @DisplayName("invalid: birth date null")
    void testCustomerDtoBirthDateNull() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                null,
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.CUSTOMER_BIRTHDATE_NULL)))
        );
    }


    @Test
    @DisplayName("invalid: birth date in the future")
    void testCustomerDtoBirthDateFuture() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(2027, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.CUSTOMER_BIRTHDATE_FUTURE)))
        );
    }

    @Test
    @DisplayName("invalid: birth date today")
    void testCustomerDtoBirthDateToday() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.now(),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.CUSTOMER_BIRTHDATE_FUTURE)))
        );
    }

    @Test
    @DisplayName("invalid: address dto null")
    void testCustomerDtoAddressDtoNull() {
        List<UUID> licencesId = getLicencesId();

        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                null,
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals(Messages.CUSTOMER_ADDRESS_NULL)))
        );
    }


    @Test
    @DisplayName("customer request dto with list of licence Ids null OK")
    void testCustomerDtoLicencesIdNull() {
        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe", "John", "johndoe@email.com", "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"), null);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty()); //Pas de contrainte not null sur les permis donc pas de violation
    }

    @Test
    @DisplayName("customer request dto with list of licence Ids empty OK")
    void testCustomerDtoLicencesListEmpty() {
        List<UUID> licencesId = new ArrayList<>();
        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Assertions.assertAll(
                () -> Assertions.assertTrue(dto.licencesId().isEmpty()),
                () -> Assertions.assertEquals(0, dto.licencesId().size())
        );
    }

    private List<UUID> getLicencesId() {
        return List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }
}