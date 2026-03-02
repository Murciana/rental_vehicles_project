package com.accenture.rentalvehiclesapp.controller;

import com.accenture.rentalvehiclesapp.controller.advice.ErrorDto;
import com.accenture.rentalvehiclesapp.service.LicenceService;
import com.accenture.rentalvehiclesapp.service.dto.LicenceRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.LicenceResponseDto;
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
@RequestMapping("/licences")
public class LicenceController {
    private final LicenceService licenceService;
    private final MessageSource messageSource;


    public LicenceController(LicenceService licenceService, MessageSource messageSource) {
        this.licenceService = licenceService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Add a new licence")
    @ApiResponse(responseCode = "201", description = "Licence Created")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    public ResponseEntity<LicenceResponseDto> add(@RequestBody @Valid LicenceRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(this.licenceService.save(requestDto));
    }

    @Operation(summary = "List every licences")
    @ApiResponse(responseCode = "200", description = "Licences list")
    @GetMapping
    public ResponseEntity<List<LicenceResponseDto>> getAll(){
        return ResponseEntity.ok(licenceService.findAll());
    }

    @Operation(summary = "Get a licence with its Id")
    @ApiResponse(responseCode = "200", description = "Licence found")
    @ApiResponse(responseCode = "404", description = "Licence not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{id}")
    public ResponseEntity<LicenceResponseDto> getById(@Parameter(description = "Licence Id", required = true) @PathVariable UUID id){
        return ResponseEntity.ok(licenceService.findById(id));
    }

}
