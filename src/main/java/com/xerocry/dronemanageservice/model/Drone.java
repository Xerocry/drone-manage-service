package com.xerocry.dronemanageservice.model;

import com.xerocry.dronemanageservice.dto.Model;
import com.xerocry.dronemanageservice.dto.Status;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
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
    private Status status = Status.IDLE;

    private double curWeight;



}
