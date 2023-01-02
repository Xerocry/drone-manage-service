package com.xerocry.dronemanageservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class DroneRequest {
    @NotBlank
    @NotNull
    @Size(max = 100, message = "Maximum 100 characters!")
    private String serialNumber;

    @Min(value = 0, message = "Weight Limit can't be less then 0gr")
    @Max(value = 500, message = "Weight Limit can't be more then 500gr")
    private double weightLimit;

    private Model model;

}
