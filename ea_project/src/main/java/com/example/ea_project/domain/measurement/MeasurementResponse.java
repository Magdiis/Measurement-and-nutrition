package com.example.ea_project.domain.measurement;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MeasurementResponse {
    @NotNull
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    @Min(0)
    private double weight;

    @NotNull
    private UUID userId;

    public MeasurementResponse(Measurement measurement){
        this.id = measurement.getId();
        this.date = measurement.getDate();
        this.weight = measurement.getWeight();
        this.userId = measurement.getUser().getId();
    }
}
