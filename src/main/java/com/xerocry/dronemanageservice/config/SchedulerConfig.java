package com.xerocry.dronemanageservice.config;

import com.xerocry.dronemanageservice.model.Drone;
import com.xerocry.dronemanageservice.repository.DroneRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@AllArgsConstructor
@Slf4j
public class SchedulerConfig {
    private DroneRepo droneRepo;

    @Scheduled(fixedDelayString = "${drone-battery-check-interval}")
    private void checkDroneBattery() {
        for (Drone drone : droneRepo.findAll()) {
            log.debug("Drone {}, Charge: {}%", drone.getSerialNumber(), drone.getCapacity());
        }
    }

}
