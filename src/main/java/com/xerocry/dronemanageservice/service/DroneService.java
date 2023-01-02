package com.xerocry.dronemanageservice.service;


import com.xerocry.dronemanageservice.dto.DroneRequest;
import com.xerocry.dronemanageservice.dto.DroneResponse;
import com.xerocry.dronemanageservice.dto.LoadRequest;
import com.xerocry.dronemanageservice.repository.DroneRepo;

import java.util.List;

public interface DroneService {
    DroneResponse registerDrone(DroneRequest droneRequest);

    DroneResponse loadDroneWithMedication(LoadRequest loadRequest);

    List<DroneResponse> findAllAvailableDrones();

    DroneResponse findDroneBySerialNumber(String serial);

}
