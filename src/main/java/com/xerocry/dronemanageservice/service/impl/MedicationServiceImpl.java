package com.xerocry.dronemanageservice.service.impl;

import com.xerocry.dronemanageservice.dto.LoadRequest;
import com.xerocry.dronemanageservice.model.Drone;
import com.xerocry.dronemanageservice.model.Medication;
import com.xerocry.dronemanageservice.service.MedicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

    @Override
    public Medication loadDrone(Drone drone, LoadRequest request) {
        return null;
    }

    @Override
    public List<Medication> getMedicationsFromDrone(String serial) {
        return null;
    }
}
