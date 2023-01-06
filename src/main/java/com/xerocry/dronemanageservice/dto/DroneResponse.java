package com.xerocry.dronemanageservice.dto;

import com.xerocry.dronemanageservice.model.Drone;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DroneResponse {
    private String serialNumber;
    private Model model;
    private Status status;
    private double weightLimit;
    private BigDecimal capacity;
    private double curWeight;

    public DroneResponse(Drone drone) {
        this.serialNumber = drone.getSerialNumber();
        this.capacity = drone.getCapacity();
        this.curWeight = drone.getCurWeight();
        this.model = drone.getModel();
        this.status = drone.getStatus();
        this.weightLimit = drone.getWeightLimit();
    }
}
