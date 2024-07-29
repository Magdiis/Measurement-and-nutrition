package com.example.ea_project.domain.user.responses;

import com.example.ea_project.domain.user.model.UserMetabolism;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMetabolismResponse {
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

    public UserMetabolismResponse(UserMetabolism userMetabolism){
        this.username = userMetabolism.getUsername();
        this.currentWeight = userMetabolism.getCurrentWeight();
        this.basalMetabolism = userMetabolism.getBasalMetabolism();
        this.recommendedCalorieIntake = userMetabolism.getRecommendedCalorieIntake();
        this.recommendedFats = userMetabolism.getRecommendedFats();
        this.recommendedProteins = userMetabolism.getRecommendedProteins();
        this.recommendedCarbohydrates = userMetabolism.getRecommendedCarbohydrates();
    }
}
