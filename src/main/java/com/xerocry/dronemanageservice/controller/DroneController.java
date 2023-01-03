package com.xerocry.dronemanageservice.controller;

import com.xerocry.dronemanageservice.dto.DroneRequest;
import com.xerocry.dronemanageservice.dto.DroneResponse;
import com.xerocry.dronemanageservice.dto.LoadRequest;
import com.xerocry.dronemanageservice.model.Medication;
import com.xerocry.dronemanageservice.service.impl.DroneServiceImpl;
import com.xerocry.dronemanageservice.service.impl.MedicationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/drones")
@RequiredArgsConstructor
public class DroneController {

    private final DroneServiceImpl droneService;
    private final MedicationServiceImpl medicationService;


    @PostMapping("/register")
    public DroneResponse registerDrone(@Validated @RequestBody DroneRequest request) {
        return droneService.registerDrone(request);
    }

    @PostMapping("load-drone")
    public DroneResponse loadMedications(@Validated @RequestBody LoadRequest request) {
        return droneService.loadDroneWithMedication(request);
    }

    @GetMapping("get-available-drones")
    public List<DroneResponse> findAvailableDrones() {
        return droneService.findAllAvailableDrones();
    }

    @GetMapping("get-drone-charge")
    public DroneResponse getDroneBatteryLevel(@RequestParam String serialNumber) {
        return droneService.findDroneBySerialNumber(serialNumber);
    }

    @GetMapping("get-medications")
    public List<Medication> getMedicationsFromDrone(@RequestParam String serial) {
        return medicationService.getMedicationsFromDrone(serial);
    }

}
