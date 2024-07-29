package com.example.ea_project.domain.measurement;

import com.example.ea_project.domain.user.UserService;
import com.example.ea_project.utils.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MeasurementRequest {
    @Schema(example = "2024-01-01")
    @NotNull
    private LocalDate date;

    @Schema(example = "60.0")
    @NotNull
    @Min(0)
    private double weight;

    @Schema(example = "7acb1eb3-1472-4da6-bf0d-8ed838da7971")
    @NotNull
    private UUID userId;

    public void toMeasurement(Measurement measurement, UserService userService){
        measurement.setDate(this.date);
        measurement.setWeight(this.weight);
        measurement.setUser(userService.getUserById(this.userId).orElseThrow(NotFoundException::new));
    }
}
