package com.xerocry.dronemanageservice.service;


import com.xerocry.dronemanageservice.dto.LoadRequest;
import com.xerocry.dronemanageservice.model.Drone;
import com.xerocry.dronemanageservice.model.Medication;

import java.util.List;

public interface MedicationService {

    Medication loadDrone(Drone drone, LoadRequest request);

    List<Medication> getMedicationsFromDrone(String serial);

}
