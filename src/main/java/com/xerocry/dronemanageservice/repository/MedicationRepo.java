package com.xerocry.dronemanageservice.repository;

import com.xerocry.dronemanageservice.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepo extends JpaRepository<Medication, Long> {
    List<Medication> findAllByDrone_SerialNumber(String serialNumber);
}
