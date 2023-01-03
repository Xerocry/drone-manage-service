package com.xerocry.dronemanageservice.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "medications")
/*name (allowed only letters, numbers, ‘-‘, ‘_’);
weight;
code (allowed only upper case letters, underscore and numbers);
image (picture of the medication case).*/
public class Medication extends BaseEntity{
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "drone_id")
    private Drone drone;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String code;

}
