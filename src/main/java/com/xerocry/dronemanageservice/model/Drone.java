package com.xerocry.dronemanageservice.model;

import com.xerocry.dronemanageservice.dto.Model;
import com.xerocry.dronemanageservice.dto.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/*
serial number (100 characters max);
model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
weight limit (500gr max);
battery capacity (percentage);
state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name = "drones")
public class Drone extends BaseEntity {
    @Column(nullable = false)
    private String serialNumber;
    @Column(nullable = false)
    private double weightLimit;
    @Column(nullable = false)
    private BigDecimal capacity;

    @Enumerated(EnumType.STRING)
    private Model model;
    @Enumerated(EnumType.STRING)
    private Status status;

    private double curWeight;



}
