package com.accenture.rentalvehiclesapp.controller;

import com.accenture.rentalvehiclesapp.controller.advice.ErrorDto;
import com.accenture.rentalvehiclesapp.service.AdminService;
import com.accenture.rentalvehiclesapp.service.dto.*;
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
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final MessageSource messageSource;

    public AdminController(AdminService adminService, MessageSource messageSource) {
        this.adminService = adminService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Add a new admin")
    @ApiResponse(responseCode = "201", description = "Admin Created")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    public ResponseEntity<AdminResponseDto> add(@RequestBody @Valid AdminRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(this.adminService.save(requestDto));
    }

    @Operation(summary = "List every admin")
    @ApiResponse(responseCode = "200", description = "Admins list")
    @GetMapping
    public ResponseEntity<List<AdminResponseDto>> getAll(){
        return ResponseEntity.ok(adminService.findAll());
    };

    @Operation(summary = "Get an admin with their Id")
    @ApiResponse(responseCode = "200", description = "Admin found")
    @ApiResponse(responseCode = "404", description = "Admin not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> getById(@Parameter(description = "Admin's Id", required = true) @PathVariable UUID id){
        return ResponseEntity.ok(adminService.findById(id));
    }

    @Operation(summary = "Partially modify an admin account (PATCH)")
    @ApiResponse(responseCode = "200", description = "Admin successfully partially modify")
    @ApiResponse(responseCode = "404", description = "Admin not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PatchMapping("/{id}")
    public ResponseEntity<AdminResponseDto> patch(@Parameter(description = "Admin's Id", required = true) @PathVariable UUID id,@RequestBody AdminRequestDto requestDto){
        AdminResponseDto responseDto = adminService.patch(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Admin's Id", required = true) @PathVariable UUID id){
        adminService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
