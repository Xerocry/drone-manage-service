package com.xerocry.dronemanageservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
