package com.xerocry.dronemanageservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "medications")
public class Medication extends BaseEntity{
    @ManyToOne
    @JsonIgnore
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
