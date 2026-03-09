package com.accenture.rentalvehiclesapp.service.impl;

import com.accenture.rentalvehiclesapp.exception.CustomerException;
import com.accenture.rentalvehiclesapp.repository.entity.enums.ERole;
import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Address;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Customer;
import com.accenture.rentalvehiclesapp.service.dto.AddressDto;
import com.accenture.rentalvehiclesapp.service.dto.request.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CustomerResponseDto;
import com.accenture.rentalvehiclesapp.service.fake.FakeCustomerMapper;
import com.accenture.rentalvehiclesapp.service.fake.FakeCustomerRepository;
import com.accenture.rentalvehiclesapp.service.fake.FakeLicenceRepository;
import com.accenture.rentalvehiclesapp.service.fake.FakePasswordEncoder;
import com.accenture.rentalvehiclesapp.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Customer Service Tests")
class CustomerServiceImplTest {

    private FakeCustomerRepository customerRepository;
    private FakeLicenceRepository licenceRepository;
    private CustomerServiceImpl service;

    @BeforeEach
    void setup() {
        customerRepository = new FakeCustomerRepository();
        licenceRepository = new FakeLicenceRepository();
        FakeCustomerMapper customerMapper = new FakeCustomerMapper();
        FakePasswordEncoder passwordEncoder = new FakePasswordEncoder();

        service = new CustomerServiceImpl(customerRepository, licenceRepository, customerMapper, passwordEncoder);
    }


    @Nested
    @DisplayName("Save a new customer")
    class AddingCustomerTests {

        @Test
        @DisplayName("save OK")
        void saveCustomerOk() {
            List<UUID> licencesId = getBAndA1LicencesId();

            CustomerRequestDto req = new CustomerRequestDto(
                    "Doe",
                    "John",
                    "johndoe@email.com",
                    "98&ygGG87",
                    LocalDate.of(1995, 1, 14),
                    new AddressDto("1 rue test", "29200", "Brest"),
                    licencesId);

            CustomerResponseDto res = service.save(req);


            Assertions.assertAll(
                    () -> assertEquals("Doe", res.lastName()),
                    () -> assertEquals("John", res.firstName()),
                    () -> assertEquals("johndoe@email.com", res.email()),
                    () -> assertEquals(LocalDate.of(1995, 1, 14), res.birthDate()),
                    () -> assertTrue(
                            res.registrationDate().isAfter(LocalDateTime.now().minusSeconds(5)) &&
                                    res.registrationDate().isBefore(LocalDateTime.now().plusSeconds(1)),
                            "registrationDate should be close to now"
                    ),
                    () -> assertEquals("1 rue test", res.address().street()),
                    () -> assertEquals("29200", res.address().postCode()),
                    () -> assertEquals("Brest", res.address().city()),
                    () -> assertEquals(2, res.licences().size()),
                    () -> assertEquals(1, customerRepository.store.size())
            );
        }

        @Test
        @DisplayName("invalid: dto null)")
        void saveCustomerNull() {
            assertThrows(CustomerException.class, () -> service.save(null));
        }

        @Test
        @DisplayName("invalid: email duplicate")
        void saveCustomer_EmailDuplicate() {
            List<Licence> licence = getBLicence();

            List<UUID> licenceId = getBLicenceId();

            UUID existingCustomerId = UUID.randomUUID();

            customerRepository.store.put(existingCustomerId, customer(
                    existingCustomerId,
                    "Doe",
                    "John",
                    "johndoe@email.com",
                    "98&ygGG87",
                    LocalDate.of(1995, 1, 14),
                    LocalDateTime.now(),
                    licence,
                    new Address("1 rue test", "29200", "Brest")
            ));

            CustomerRequestDto req = new CustomerRequestDto(
                    "Williams",
                    "Dylan",
                    "johndoe@email.com",
                    "abqzdA4&Pnd",
                    LocalDate.of(1990, 12, 4),
                    new AddressDto("2 rue test", "44100", "Nantes"),
                    licenceId);

            CustomerException ex = assertThrows(CustomerException.class, () -> service.save(req));
            Assertions.assertEquals(Messages.USER_EMAIL_DUPLICATE, ex.getMessage());
        }

        @Test
        @DisplayName("invalid: underage")
        void saveCustomerUnderage() {
            List<UUID> licenceId = getBLicenceId();

            CustomerRequestDto req = new CustomerRequestDto(
                    "Doe",
                    "John",
                    "johndoe@email.com",
                    "98&ygGG87",
                    LocalDate.of(2010, 1, 14),
                    new AddressDto("1 rue test", "29200", "Brest"),
                    licenceId);

            CustomerException ex = assertThrows(CustomerException.class, () -> service.save(req));
            Assertions.assertEquals(Messages.CUSTOMER_UNDERAGE, ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Delete a customer")
    class DeleteCustomer {
        @Test
        @DisplayName("customer not found")
        void deleteCustomerNotFound() {
            EntityNotFoundException ex = assertThrowsExactly(EntityNotFoundException.class, () -> service.delete(UUID.randomUUID()));
            Assertions.assertEquals(Messages.CUSTOMER_NOT_FOUND, ex.getMessage());
        }

        @Test
        @DisplayName("delete customer OK")
        void deleteCustomerOk() {
            List<Licence> licence = getBLicence();

            UUID id = UUID.randomUUID();

            customerRepository.store.put(id, customer(
                    id,
                    "Doe",
                    "John",
                    "johndoe@email.com",
                    "98&ygGG87",
                    LocalDate.of(1995, 1, 14),
                    LocalDateTime.now(),
                    licence,
                    new Address("1 rue test", "29200", "Brest")
            ));

            service.delete(id);

            assertFalse(customerRepository.existsById(id));
            assertFalse(customerRepository.store.containsKey(id));
        }
    }

    @Nested
    @DisplayName("Find customer(s)")
    class GetTests {
        @Test
        @DisplayName("find all customers OK")
        void findAllOk() {
            List<Licence> customerLicences1 = new ArrayList<>();
            customerLicences1.add(licenceRepository.findById(UUID.fromString("149c9842-e944-4597-be59-b8c6dd7a530d"))
                    .orElseThrow(() -> new EntityNotFoundException(Messages.CUSTOMER_NOT_FOUND)));

            List<Licence> customerLicences2 = new ArrayList<>();

            UUID c1Id = UUID.randomUUID();
            UUID c2Id = UUID.randomUUID();


            customerRepository.store.put(c1Id, customer(
                    c1Id,
                    "Doe",
                    "John",
                    "johndoe@email.com",
                    "98&ygGG87",
                    LocalDate.of(1995, 1, 14),
                    LocalDateTime.now(),
                    customerLicences1,
                    new Address("1 rue test", "29200", "Brest")
            ));

            customerRepository.store.put(c2Id, customer(
                    c2Id,
                    "Williams",
                    "Dylan",
                    "dylanwilliams@email.com",
                    "abqzdA4&Pnd",
                    LocalDate.of(1990, 12, 4),
                    LocalDateTime.now(),
                    customerLicences2,
                    new Address("2 rue test", "44100", "Nantes")
            ));

            List<CustomerResponseDto> res = service.findAll();
            Assertions.assertAll(
                    () -> assertEquals(2, res.size()),
                    () -> assertFalse(customerRepository.findById(c1Id).orElseThrow().getLicences().isEmpty()),
                    () -> assertTrue(customerRepository.findById(c2Id).orElseThrow().getLicences().isEmpty()),
                    () -> assertEquals(customerLicences2, customerRepository.findById(c2Id).orElseThrow().getLicences())
            );
        }

        @Test
        @DisplayName("by Id: not found")
        void findByIdNotFound() {
            EntityNotFoundException ex = assertThrowsExactly(EntityNotFoundException.class, () -> service.findById(UUID.randomUUID()));
            Assertions.assertEquals(Messages.CUSTOMER_NOT_FOUND, ex.getMessage());
        }

        @Test
        @DisplayName("by Id: OK")
        void findByIdOk() {
            List<Licence> licence = getBLicence();

            UUID id = UUID.randomUUID();

            customerRepository.store.put(id, customer(
                    id,
                    "Doe",
                    "John",
                    "johndoe@email.com",
                    "98&ygGG87",
                    LocalDate.of(1995, 1, 14),
                    LocalDateTime.now(),
                    licence,
                    new Address("1 rue test", "29200", "Brest")
            ));

            CustomerResponseDto res = service.findById(id);

            assertEquals(id, res.id());
            assertEquals("John", res.firstName());
        }

        @Test
        @DisplayName("by email: not found")
        void findByEmailNotFound(){
            EntityNotFoundException ex = assertThrowsExactly(EntityNotFoundException.class, () -> service.findByEmail("johndoe@email.com"));
            Assertions.assertEquals(Messages.CUSTOMER_NOT_FOUND, ex.getMessage());
        }

        @Test
        @DisplayName("by email: OK")
        void findByEmailOk(){
            List<Licence> licence = getBLicence();

            UUID id = UUID.randomUUID();

            customerRepository.store.put(id, customer(
                    id,
                    "Doe",
                    "John",
                    "johndoe@email.com",
                    "98&ygGG87",
                    LocalDate.of(1995, 1, 14),
                    LocalDateTime.now(),
                    licence,
                    new Address("1 rue test", "29200", "Brest")
            ));

            CustomerResponseDto res = service.findByEmail("johndoe@email.com");

            assertEquals("johndoe@email.com", res.email());
        }
    }


    @Nested
    @DisplayName("Update a customer")
    class UpdateCustomerTests {

        @Test
        @DisplayName("update OK")
        void patchCustomerOk() {
            List<Licence> licences = new ArrayList<>();

            UUID id = UUID.randomUUID();

            customerRepository.store.put(id, customer(
                    id,
                    "Doe",
                    "John",
                    "johndoe@email.com",
                    "98&ygGG87",
                    LocalDate.of(1995, 1, 14),
                    LocalDateTime.now(),
                    licences,
                    new Address("1 rue test", "29200", "Brest")
            ));


            List<UUID> licenceId = List.of(
                    UUID.fromString("41b0a512-69d3-49d1-ab8c-6672308bb1a2"),
                    UUID.fromString("149c9842-e944-4597-be59-b8c6dd7a530d")
            );

            CustomerRequestDto patchDto = new CustomerRequestDto("Laurent", "Michel",
                    null, null, null, null, licenceId);

            CustomerResponseDto res = service.patch(id, patchDto);

            Assertions.assertAll(
                    () -> assertEquals("Laurent", res.lastName()),
                    () -> assertEquals("Michel", res.firstName()),
                    () -> assertEquals("johndoe@email.com", res.email()),
                    () -> assertEquals(2, res.licences().size())
            );
        }

        @Test
        @DisplayName("customer not found")
        void patchCustomerIdNotFound() {

            CustomerRequestDto patchDto = new CustomerRequestDto("Laurent", "Michel",
                    null, null, null, null, null);

            EntityNotFoundException ex = assertThrowsExactly(EntityNotFoundException.class, () -> service.patch(UUID.randomUUID(), patchDto));
            Assertions.assertEquals(Messages.CUSTOMER_NOT_FOUND, ex.getMessage());

        }

        @Test
        @DisplayName("nothing to update")
        void updateCustomerNoUpdate() {
            List<Licence> licences = new ArrayList<>();

            UUID id = UUID.randomUUID();

            customerRepository.store.put(id, customer(
                    id,
                    "Doe",
                    "John",
                    "johndoe@email.com",
                    "98&ygGG87",
                    LocalDate.of(1995, 1, 14),
                    LocalDateTime.now(),
                    licences,
                    new Address("1 rue test", "29200", "Brest")
            ));

            CustomerRequestDto requestDto = new CustomerRequestDto(" ", " ",
                    " ", " ", null, null, null);

            CustomerResponseDto res = service.patch(id, requestDto);

            assertEquals("Doe", res.lastName());
            assertEquals("johndoe@email.com", res.email());
            assertTrue(res.licences().isEmpty());
        }

    }


    private List<UUID> getBLicenceId() {
        return List.of(
                UUID.fromString("149c9842-e944-4597-be59-b8c6dd7a530d")// B
        );
    }

    private List<Licence> getBLicence() {
        List<Licence> licence = new ArrayList<>();
        licence.add(licenceRepository.findById(UUID.fromString("149c9842-e944-4597-be59-b8c6dd7a530d"))
                .orElseThrow(() -> new EntityNotFoundException(Messages.LICENCE_NOT_FOUND)));
        return licence;
    }

    private List<UUID> getBAndA1LicencesId() {
        return List.of(
                UUID.fromString("149c9842-e944-4597-be59-b8c6dd7a530d"),
                UUID.fromString("41b0a512-69d3-49d1-ab8c-6672308bb1a2")
        );
    }

    private Customer customer(UUID id, String lastName, String firstName, String email, String password, LocalDate birthDate, LocalDateTime registrationDate, List<Licence> licences, Address address) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setLastName(lastName);
        customer.setFirstName(firstName);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setRole(ERole.CUSTOMER);
        customer.setBirthDate(birthDate);
        customer.setRegistrationDate(registrationDate);
        customer.setLicences(licences);
        customer.setAddress(address);
        return customer;
    }
}