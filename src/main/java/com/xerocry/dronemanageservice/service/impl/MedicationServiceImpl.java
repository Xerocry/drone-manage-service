package com.xerocry.dronemanageservice.service.impl;

import com.xerocry.dronemanageservice.dto.LoadRequest;
import com.xerocry.dronemanageservice.model.Drone;
import com.xerocry.dronemanageservice.model.Medication;
import com.xerocry.dronemanageservice.repository.MedicationRepo;
import com.xerocry.dronemanageservice.service.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepo medicationRepo;

    @Override
    public Medication loadDrone(Drone drone, LoadRequest request) {
        return medicationRepo.save(Medication.builder()
                .drone(drone)
                .weight(request.getWeight())
                .code(request.getCode())
                .image(request.getImage())
                .name(request.getMedicationName())
                .build());
    }

    @Override
    public List<Medication> getMedicationsFromDrone(String serial) {
        return medicationRepo.findAllByDrone_SerialNumber(serial);
    }
}
