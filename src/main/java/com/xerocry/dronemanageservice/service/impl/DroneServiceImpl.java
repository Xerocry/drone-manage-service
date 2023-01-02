package com.xerocry.dronemanageservice.service.impl;

import com.xerocry.dronemanageservice.dto.DroneRequest;
import com.xerocry.dronemanageservice.dto.DroneResponse;
import com.xerocry.dronemanageservice.dto.LoadRequest;
import com.xerocry.dronemanageservice.dto.Status;
import com.xerocry.dronemanageservice.exception.DroneBatteryException;
import com.xerocry.dronemanageservice.exception.DroneOverweightException;
import com.xerocry.dronemanageservice.exception.DroneStatusException;
import com.xerocry.dronemanageservice.exception.NotFoundException;
import com.xerocry.dronemanageservice.model.Drone;
import com.xerocry.dronemanageservice.model.Medication;
import com.xerocry.dronemanageservice.repository.DroneRepo;
import com.xerocry.dronemanageservice.service.DroneService;
import com.xerocry.dronemanageservice.service.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepo droneRepo;
    private final MedicationService medicationServiceImpl;


    @Override
    public DroneResponse registerDrone(DroneRequest droneRequest) {
        Drone drone = droneRepo.save(Drone.builder()
                        .serialNumber(droneRequest.getSerialNumber())
                        .weightLimit(droneRequest.getWeightLimit())
                        .curWeight(0)
                        .capacity(new BigDecimal(100))
                        .model(droneRequest.getModel())
                        .status(Status.IDLE)
                .build());
        return new DroneResponse(drone);
    }

    @Override
    public DroneResponse loadDroneWithMedication(LoadRequest loadRequest) {
        Drone drone = droneRepo.findBySerialNumber(loadRequest.getDroneSerialNumber()).orElseThrow(() -> new NotFoundException("No Drone with given serial number was found!"));

        if (!drone.getStatus().equals(Status.IDLE) || !drone.getStatus().equals(Status.LOADING)) {
            throw new DroneStatusException("Drone in the wrong state and cannot be loaded");
        }

        if (drone.getCapacity().compareTo(BigDecimal.valueOf(25)) < 0) {
            throw new DroneBatteryException("Not enough battery charge to load!");

        }

        if (drone.getWeightLimit() < loadRequest.getWeight()) {
            throw new DroneOverweightException("Load is too heavy for this drone!");
        }

        drone.setStatus(Status.LOADING);

        Medication medication = medicationServiceImpl.loadDrone(drone, loadRequest);

        drone.setStatus(Status.LOADED);

        DroneResponse droneResponse = new DroneResponse(drone);
        droneResponse.setStatus(Status.DELIVERING);
        droneResponse.setCurWeight(medication.getWeight());
        return droneResponse;
    }

    @Override
    public List<DroneResponse> findAllAvailableDrones() {
        return null;
    }

    @Override
    public DroneResponse findDroneBySerialNumber(String serial) {
        return null;
    }
}
