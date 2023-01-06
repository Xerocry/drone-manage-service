package com.xerocry.dronemanageservice.dto;

import javax.validation.constraints.*;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class LoadRequest {
    @NotBlank
    private String droneSerialNumber;

    @Pattern(regexp = "^[a-zA-Z0-9\\-\\_]+$")
    @NotBlank
    private String medicationName;

    @NotNull
    private double weight;

    @Pattern(regexp = "^[A-Z0-9\\_]+$")
    private String code;

    private String image;
}
