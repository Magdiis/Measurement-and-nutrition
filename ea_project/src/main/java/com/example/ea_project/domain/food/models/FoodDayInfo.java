package com.example.ea_project.domain.food.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDayInfo {
    @NotNull
    private LocalDate date;

    @NotNull
    private double caloriesInTotal;

    @NotNull
    private double fatsInTotal;

    @NotNull
    private double proteinsInTotal;

    @NotNull
    private double carbohydratesInTotal;

    @NotEmpty
    private String username;
}
