package com.accenture.rentalvehiclesapp.controller.vehicle;

import com.accenture.rentalvehiclesapp.controller.advice.ErrorDto;
import com.accenture.rentalvehiclesapp.service.MotoService;
import com.accenture.rentalvehiclesapp.service.dto.patch.MotoPatchDto;
import com.accenture.rentalvehiclesapp.service.dto.request.MotoRequestDto;
import com.accenture.rentalvehiclesapp.service.dto.response.MotoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/motorcycles")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @Operation(summary = "Add a new motorcycle")
    @ApiResponse(responseCode = "201", description = "Motorcycle Created")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    public ResponseEntity<MotoResponseDto> add(@RequestBody @Valid MotoRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.motoService.save(requestDto));
    }

    @Operation(summary = "List every motorcycle")
    @ApiResponse(responseCode = "200", description = "Motorcycles list")
    @GetMapping
    public ResponseEntity<List<MotoResponseDto>> getAll(){
        return ResponseEntity.ok(motoService.findAll());
    }

    @Operation(summary = "Get a motorcycle with its Id")
    @ApiResponse(responseCode = "200", description = "Motorcycle found")
    @ApiResponse(responseCode = "404", description = "Motorcycle not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{id}")
    public ResponseEntity<MotoResponseDto> getById(@Parameter(description = "Motorcycle Id", required = true) @PathVariable UUID id){
        return ResponseEntity.ok(motoService.findById(id));
    }

    @Operation(summary = "Partially modify a motorcycle (PATCH)")
    @ApiResponse(responseCode = "200", description = "Motorcycle successfully partially modify")
    @ApiResponse(responseCode = "404", description = "Motorcycle not found",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PatchMapping("/{id}")
    public ResponseEntity<MotoResponseDto> patch(@Parameter(description = "Motorcycle Id", required = true) @PathVariable UUID id,@RequestBody MotoPatchDto patchDto){
        MotoResponseDto responseDto = motoService.patch(id, patchDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Delete a motorcycle with its Id")
    @ApiResponse(responseCode = "204", description = "Motorcycle successfully deleted")
    @ApiResponse(responseCode = "200", description = "Impossible to delete - Motorcycle removed from park",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Parameter(description = "Motorcycle Id", required = true) @PathVariable UUID id){

        motoService.delete(id);
        if(motoService.existsById(id)){
            return ResponseEntity.ok("Impossible to delete - Vehicle removed from park");
        } else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
