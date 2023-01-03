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
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
//@Transactional(Transactional.TxType.REQUIRES_NEW)
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
    /*
    * TODO:
    *   1. Asynch annotation
    *   2. Timeout for loading
    *   3. Return id instead of object?
    */
    public DroneResponse loadDroneWithMedication(LoadRequest loadRequest) {
        Drone drone = droneRepo.findBySerialNumber(loadRequest.getDroneSerialNumber()).orElseThrow(() -> new NotFoundException("No Drone with given serial number was found!"));

        if (!drone.getStatus().equals(Status.IDLE)) {
            throw new DroneStatusException("Drone in the wrong state and cannot be loaded");
        }
        updateDrone(drone, Status.LOADING);

        if (drone.getCapacity().compareTo(BigDecimal.valueOf(25)) < 0) {
            throw new DroneBatteryException("Not enough battery charge to load!");
        }

        if (drone.getWeightLimit() < loadRequest.getWeight() + drone.getCurWeight()) {
            throw new DroneOverweightException("Load is too heavy for this drone!");
        }

        drone.setCurWeight(drone.getCurWeight() + loadRequest.getWeight());

        Medication medication = medicationServiceImpl.loadDrone(drone, loadRequest);
        updateDrone(drone, Status.LOADED);

        DroneResponse droneResponse = new DroneResponse(drone);
        droneResponse.setCurWeight(medication.getWeight());
        return droneResponse;
    }

    @Override
    public List<DroneResponse> findAllAvailableDrones() {
        List<Drone> drones = droneRepo.findAllByStatus(Status.IDLE);

        Function<Drone, DroneResponse> availableDrones = (drone) -> DroneResponse.builder()
                .weightLimit(drone.getWeightLimit())
                .serialNumber(drone.getSerialNumber())
                .capacity(drone.getCapacity())
                .curWeight(drone.getCurWeight())
                .model(drone.getModel()).build();
        return drones.stream().map(availableDrones).collect(Collectors.toList());
    }

    @Override
    public DroneResponse findDroneBySerialNumber(String serial) {
        Drone drone = droneRepo.findBySerialNumber(serial).orElseThrow(() -> new NotFoundException("There is no drone with a given serial!"));
        return new DroneResponse(drone);
    }

    private void updateDrone(Drone drone, Status status) {
        drone.setStatus(status);
        droneRepo.save(drone);
    }

}
