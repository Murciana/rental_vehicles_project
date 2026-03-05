package com.accenture.rentalvehiclesapp.controller.vehicle;

import com.accenture.rentalvehiclesapp.controller.advice.ErrorDto;
import com.accenture.rentalvehiclesapp.service.BicycleService;
import com.accenture.rentalvehiclesapp.service.dto.patch.BicyclePatchDto;
import com.accenture.rentalvehiclesapp.service.dto.patch.CarPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.BicycleRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.request.CarRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.BicycleResponseDto;
import com.accenture.rentalvehiclesapp.service.dto.response.CarResponseDto;
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
@RequestMapping("/bicycles")
public class BicycleController {

    private final BicycleService bicycleService;
    private final MessageSource messageSource;

    public BicycleController(BicycleService bicycleService, MessageSource messageSource) {
        this.bicycleService = bicycleService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Add a new bicycle")
    @ApiResponse(responseCode = "201", description = "Bicycle Created")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    public ResponseEntity<BicycleResponseDto> add(@RequestBody @Valid BicycleRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bicycleService.save(requestDto));
    }

    @Operation(summary = "List every bicycle")
    @ApiResponse(responseCode = "200", description = "Bicycles list")
    @GetMapping
    public ResponseEntity<List<BicycleResponseDto>> getAll(){
        return ResponseEntity.ok(bicycleService.findAll());
    }

    @Operation(summary = "Get a bicycle with its Id")
    @ApiResponse(responseCode = "200", description = "Bicycle found")
    @ApiResponse(responseCode = "404", description = "Bicycle not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{id}")
    public ResponseEntity<BicycleResponseDto> getById(@Parameter(description = "Bicycle Id", required = true) @PathVariable UUID id){
        return ResponseEntity.ok(bicycleService.findById(id));
    }

    @Operation(summary = "Partially modify a bicycle (PATCH)")
    @ApiResponse(responseCode = "200", description = "Bicycle successfully partially modify")
    @ApiResponse(responseCode = "404", description = "Bicycle not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PatchMapping("/{id}")
    public ResponseEntity<BicycleResponseDto> patch(@Parameter(description = "Bicycle Id", required = true) @PathVariable UUID id,@RequestBody BicyclePatchDto patchDto){
        BicycleResponseDto responseDto = bicycleService.patch(id, patchDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Delete a bicycle with its Id")
    @ApiResponse(responseCode = "204", description = "Bicycle successfully deleted")
    @ApiResponse(responseCode = "200", description = "Impossible to delete - Vehicle removed from park",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Parameter(description = "Bicycle Id", required = true) @PathVariable UUID id){

        bicycleService.delete(id);
        if(bicycleService.existsById(id)){
            return ResponseEntity.ok("Impossible to delete - Vehicle removed from park");
        } else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
