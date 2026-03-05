package com.accenture.rentalvehiclesapp.controller.vehicle;

import com.accenture.rentalvehiclesapp.controller.advice.ErrorDto;
import com.accenture.rentalvehiclesapp.service.CarService;
import com.accenture.rentalvehiclesapp.service.dto.patch.CarPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.CarRequestDto;
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
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final MessageSource messageSource;

    public CarController(CarService carService, MessageSource messageSource) {
        this.carService = carService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Add a new car")
    @ApiResponse(responseCode = "201", description = "Car Created")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    public ResponseEntity<CarResponseDto> add(@RequestBody @Valid CarRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.carService.save(requestDto));
    }

    @Operation(summary = "List every car")
    @ApiResponse(responseCode = "200", description = "Cars list")
    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAll(){
        return ResponseEntity.ok(carService.findAll());
    }

    @Operation(summary = "Get a car with its Id")
    @ApiResponse(responseCode = "200", description = "Car found")
    @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getById(@Parameter(description = "Car Id", required = true) @PathVariable UUID id){
        return ResponseEntity.ok(carService.findById(id));
    }

    @Operation(summary = "Partially modify a car (PATCH)")
    @ApiResponse(responseCode = "200", description = "Car successfully partially modify")
    @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PatchMapping("/{id}")
    public ResponseEntity<CarResponseDto> patch(@Parameter(description = "Car Id", required = true) @PathVariable UUID id,@RequestBody CarPatchDto patchDto){
        CarResponseDto responseDto = carService.patch(id, patchDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Delete a car with its Id")
    @ApiResponse(responseCode = "204", description = "Car successfully deleted")
    @ApiResponse(responseCode = "200", description = "Impossible to delete - Car removed from park",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Parameter(description = "Car Id", required = true) @PathVariable UUID id){

        carService.delete(id);
        if(carService.existsById(id)){
            return ResponseEntity.ok("Impossible to delete - Car removed from park");
        } else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
