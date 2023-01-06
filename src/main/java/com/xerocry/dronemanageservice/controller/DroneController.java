package com.xerocry.dronemanageservice.controller;

import com.xerocry.dronemanageservice.dto.DroneRequest;
import com.xerocry.dronemanageservice.dto.DroneResponse;
import com.xerocry.dronemanageservice.dto.LoadRequest;
import com.xerocry.dronemanageservice.exception.NotFoundException;
import com.xerocry.dronemanageservice.model.Drone;
import com.xerocry.dronemanageservice.model.Medication;
import com.xerocry.dronemanageservice.service.impl.DroneServiceImpl;
import com.xerocry.dronemanageservice.service.impl.MedicationServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/drones")
@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8100")},
        info = @Info(title = "Drone Manager API", version = "v1", description = "Part of Drone managment API handling drone control"))
@RequiredArgsConstructor
public class DroneController {

    private final DroneServiceImpl droneService;
    private final MedicationServiceImpl medicationService;

    @Operation(summary = "Register new drone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registred",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DroneResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Drone validation failed!",
                    content = @Content)})
    @PostMapping("/register")
    public DroneResponse registerDrone(@Validated @RequestBody DroneRequest request) {
        return droneService.registerDrone(request);
    }

    @Operation(summary = "Load drone with medication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DroneResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid medication parameters",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Drone is overloaded, not charged or busy",
                    content = @Content)})
    @PostMapping("load-drone")
    public DroneResponse loadMedications(@Validated @RequestBody LoadRequest request) {
        return droneService.loadDroneWithMedication(request);
    }

    @Operation(summary = "Find drones that are available for loading ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully founded",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DroneResponse.class))) }),
            @ApiResponse(responseCode = "500", description = "No available drones are found!",
                    content = @Content)})
    @GetMapping("get-available-drones")
    public List<DroneResponse> findAvailableDrones() {
        List<DroneResponse> list = droneService.findAllAvailableDrones();
        if (list.isEmpty()) {
            throw new NotFoundException("No available drones are found!");
        }
        return list;
    }

    @Operation(summary = "Check particular drone battery level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned battery level",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "500", description = "There is no drone with a given serial",
                    content = @Content)})
    @GetMapping("get-drone-charge/{serial}")
    public Map<String, Object> getDroneBatteryLevel(@PathVariable String serial) {
        DroneResponse drone = droneService.findDroneBySerialNumber(serial);
        if (drone == null) {
            throw new NotFoundException("There is no drone with a given serial!");
        }
        return Map.of("Drone", drone.getSerialNumber(), "Charge Level", drone.getCapacity());
    }

    @Operation(summary = "Get all medication loaded on a particular drone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned medication list",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Medication.class))) }),
            @ApiResponse(responseCode = "500", description = "There is no drone with a given serial",
                    content = @Content)})
    @GetMapping("get-medications/{serial}")
    public List<Medication> getMedicationsFromDrone(@PathVariable String serial) {

        DroneResponse drone = droneService.findDroneBySerialNumber(serial);
        if (drone == null) {
            throw new NotFoundException("There is no drone with a given serial!");
        }
        List<Medication> resultList = medicationService.getMedicationsFromDrone(serial);
        if (resultList.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "Drone is empty!");
        } else
            return resultList;
    }

}
