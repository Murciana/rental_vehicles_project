package com.accenture.rentalvehiclesapp.controller;

import com.accenture.rentalvehiclesapp.controller.advice.ErrorDto;
import com.accenture.rentalvehiclesapp.service.CustomerService;
import com.accenture.rentalvehiclesapp.service.dto.CustomerRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.CustomerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final MessageSource messageSource;

    public CustomerController(CustomerService customerService, MessageSource messageSource) {
        this.customerService = customerService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Add a new customer")
    @ApiResponse(responseCode = "201", description = "Customer Created")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    public ResponseEntity<CustomerResponseDto> add(@RequestBody @Valid CustomerRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.customerService.save(requestDto));
    }

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

}
