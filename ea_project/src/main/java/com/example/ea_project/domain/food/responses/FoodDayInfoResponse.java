package com.example.ea_project.domain.food.responses;

import com.example.ea_project.domain.food.FoodService;
import com.example.ea_project.domain.food.models.Food;
import com.example.ea_project.domain.food.models.FoodDayInfo;
import com.example.ea_project.domain.user.UserService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDayInfoResponse {
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

    public FoodDayInfoResponse(FoodDayInfo foodDayInfo){
        this.date = foodDayInfo.getDate();
        this.caloriesInTotal = foodDayInfo.getCaloriesInTotal();
        this.fatsInTotal = foodDayInfo.getFatsInTotal();
        this.proteinsInTotal = foodDayInfo.getProteinsInTotal();
        this.carbohydratesInTotal = foodDayInfo.getCarbohydratesInTotal();
        this.username = foodDayInfo.getUsername();
    }
}
