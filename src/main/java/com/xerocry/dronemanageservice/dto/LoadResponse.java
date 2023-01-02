package com.xerocry.dronemanageservice.dto;

import com.xerocry.dronemanageservice.model.Drone;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoadResponse {

    private Drone drone;



}
