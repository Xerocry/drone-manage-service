package com.xerocry.dronemanageservice.repository;

import com.xerocry.dronemanageservice.dto.Status;
import com.xerocry.dronemanageservice.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepo extends JpaRepository<Drone, Long> {
    Optional<Drone> findBySerialNumber(String serial);

    List<Drone> findAllByStatus(Status status);
}
