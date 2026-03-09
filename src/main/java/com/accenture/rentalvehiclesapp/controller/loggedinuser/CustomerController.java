package com.accenture.rentalvehiclesapp.controller.loggedinuser;

import com.accenture.rentalvehiclesapp.controller.advice.ErrorDto;
import com.accenture.rentalvehiclesapp.service.CustomerService;
import com.accenture.rentalvehiclesapp.service.dto.request.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CustomerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Add a new customer")
    @ApiResponse(responseCode = "201", description = "Customer Created")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    public ResponseEntity<CustomerResponseDto> add(@RequestBody @Valid CustomerRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.customerService.save(requestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List every customer")
    @ApiResponse(responseCode = "200", description = "Customers list")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @Operation(summary = "Get a customer with their Id")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getById(@Parameter(description = "Customer's Id", required = true) @PathVariable UUID id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponseDto> getPersonalDetails(@RequestHeader(name = "authorization") String base64Header) {

        byte[] decoded = Base64.getDecoder().decode(base64Header.split(" ")[1]);
        String content = new String(decoded, StandardCharsets.UTF_8);

        String email = content.split(":")[0];     // Le contenu décodé est "email:password"

        CustomerResponseDto responseDto = customerService.findByEmail(email);

        if (responseDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Partially modify a customer account (PATCH)")
    @ApiResponse(responseCode = "200", description = "Customer successfully partially modify")
    @ApiResponse(responseCode = "404", description = "Customer not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> patch(@Parameter(description = "Customer's Id", required = true) @PathVariable UUID id, @RequestBody CustomerRequestDto requestDto) {
        CustomerResponseDto responseDto = customerService.patch(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/me")
    public ResponseEntity<CustomerResponseDto> patch( @RequestBody CustomerRequestDto patchDto, @RequestHeader(name = "authorization") String base64Header) {

        byte[] decoded = Base64.getDecoder().decode(base64Header.split(" ")[1]);
        String content = new String(decoded, StandardCharsets.UTF_8);

        String email = content.split(":")[0];     // Le contenu décodé est "email:password"

        CustomerResponseDto responseDto = customerService.findByEmail(email);
        customerService.patch(responseDto.id(), patchDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Customer's Id", required = true) @PathVariable UUID id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> delete(@RequestHeader(name = "authorization") String base64Header) {

        byte[] decoded = Base64.getDecoder().decode(base64Header.split(" ")[1]);
        String content = new String(decoded, StandardCharsets.UTF_8);

        String email = content.split(":")[0];     // Le contenu décodé est "email:password"

        CustomerResponseDto responseDto = customerService.findByEmail(email);
        customerService.delete(responseDto.id());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
