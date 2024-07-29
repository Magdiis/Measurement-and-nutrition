package com.example.ea_project.domain.user.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMetabolism {
    @NotEmpty
    private String username;

    @NotNull
    private Integer currentWeight;

    // calorie intake without physical exercise
    @NotNull
    private double basalMetabolism;

    // calorie intake with physical exercise
    @NotNull
    private double recommendedCalorieIntake;

    @NotNull
    private double recommendedFats;
    @NotNull
    private double recommendedProteins;
    @NotNull
    private double recommendedCarbohydrates;


}
