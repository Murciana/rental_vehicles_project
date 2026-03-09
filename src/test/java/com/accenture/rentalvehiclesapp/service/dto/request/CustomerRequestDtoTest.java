package com.accenture.rentalvehiclesapp.service.dto.request;

import com.accenture.rentalvehiclesapp.service.dto.AddressDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

class CustomerRequestDtoTest {
    // Le Validator de Jakarta permet de tester les contraintes de validation (@NotNull, @NotBlank, etc.)
    // directement sur le DTO, sans passer par un contrôleur Spring.
    private Validator validator;

    //@BeforeEach garantit qu'un nouveau Validator est créé avant chaque test.
    // ValidatorFactory est la fabrique qui construit le Validator selon la configuration Jakarta Validation.
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCustomerDtoWithLicencesOK() {

        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(customerRequestDto.lastName()),
                () -> Assertions.assertFalse(customerRequestDto.lastName().isBlank()),
                () -> Assertions.assertNotNull(customerRequestDto.firstName()),
                () -> Assertions.assertFalse(customerRequestDto.firstName().isBlank()),
                () -> Assertions.assertNotNull(customerRequestDto.email()),
                () -> Assertions.assertFalse(customerRequestDto.email().isBlank()),
                () -> Assertions.assertTrue(customerRequestDto.email().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")),
                () -> Assertions.assertNotNull(customerRequestDto.password()),
                () -> Assertions.assertFalse(customerRequestDto.password().isBlank()),
                () -> Assertions.assertTrue(customerRequestDto.password().length() >= 8),
                () -> Assertions.assertTrue(customerRequestDto.password().length() <= 16),
                () -> Assertions.assertTrue(customerRequestDto.password().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#_&§-]).{8,16}$")),
                () -> Assertions.assertNotNull(customerRequestDto.birthDate()),
                () -> Assertions.assertTrue(customerRequestDto.birthDate().isBefore(LocalDate.now())),
                () -> Assertions.assertNotNull(customerRequestDto.address()),
                () -> Assertions.assertNotNull(customerRequestDto.licencesId()),
                () -> Assertions.assertFalse(customerRequestDto.licencesId().isEmpty()),
                () -> Assertions.assertEquals(2, customerRequestDto.licencesId().size())
        );
    }

    @Test
    public void testCustomerDtoLastNameNull() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                null,
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        // validator.validate() retourne un Set de violations de contraintes.
        // Si le Set est vide, le DTO est valide. S'il contient des éléments, des contraintes sont violées.
        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        // On vérifie que la violation concerne bien le champ "titre"
        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.lastname.null")))
        );
    }

    @Test
    public void testCustomerDtoLastNameBlank() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                " ",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.lastname.null")))
        );
    }

    @Test
    public void testCustomerDtoFirstNameNull() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                null,
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.firstname.null")))
        );
    }

    @Test
    public void testCustomerDtoFirstNameBlank() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                " ",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.firstname.null")))
        );
    }

    @Test
    public void testCustomerDtoEmailNull() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                null,
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.email.null")))
        );
    }

    @Test
    public void testCustomerDtoEmailBlank() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                " ",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.email.null")))
        );
    }

    @Test
    public void testCustomerDtoEmailInvalid() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe-email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.email.invalid")))
        );
    }

    @Test
    public void testCustomerDtoPasswordNull() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                null,
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.password.null")))
        );
    }

    @Test
    public void testCustomerDtoPasswordBlank() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                " ",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.password.null")))
        );
    }

    @Test
    public void testCustomerDtoPasswordInvalidCharacters() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "'/|1GGAi",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.password.invalid")))
        );
    }

    @Test
    public void testCustomerDtoPasswordTooShort() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygG",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.password.size")))
        );
    }

    @Test
    public void testCustomerDtoPasswordTooLong() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDtoD = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG8798&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDtoD);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("user.password.size")))
        );
    }

    @Test
    public void testCustomerDtoBirthDateNull() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                null,
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("customer.birthdate.null")))
        );
    }


    @Test
    public void testCustomerDtoBirthDateFuture() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(2027, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("customer.birthdate.future")))
        );
    }

    @Test
    public void testCustomerDtoBirthDateToday() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.now(),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("customer.birthdate.future")))
        );
    }

    @Test
    public void testCustomerDtoAddressDtoNull() {
        List<UUID> licencesId = List.of(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                null,
                licencesId);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(customerRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertFalse(violations.isEmpty()),
                () -> Assertions.assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().equals("customer.address.null")))
        );
    }


    @Test
    public void testCustomerDtoLicencesIdNull() {
        CustomerRequestDto dto = new CustomerRequestDto(
                "Doe", "John", "johndoe@email.com", "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"), null);

        Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty()); //Pas de contrainte not null sur les permis donc pas de violation
    }

    @Test
    public void testCustomerDtoLicencesListEmpty() {
        List<UUID> licencesId = new ArrayList<>();
        CustomerRequestDto customerRequestDto = new CustomerRequestDto(
                "Doe",
                "John",
                "johndoe@email.com",
                "98&ygGG87",
                LocalDate.of(1995, 1, 14),
                new AddressDto("1 rue test", "29200", "Brest"),
                licencesId);

        Assertions.assertAll(
                () -> Assertions.assertTrue(customerRequestDto.licencesId().isEmpty()),
                () -> Assertions.assertEquals(0, customerRequestDto.licencesId().size())
        );
    }

}