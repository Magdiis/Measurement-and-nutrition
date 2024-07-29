package com.example.ea_project.domain.food.responses;

import com.example.ea_project.domain.food.models.Food;
import com.example.ea_project.domain.user.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FoodResponse {
    @NotNull
    private Long id;

    @NotNull
    private LocalDate date;

    // grams
    @NotNull
    private double quantity;

    @NotNull
    private Long productId;

    @NotNull
    private UUID userId;

    @NotNull
    private String name;

    @NotNull
    private double calories;

    @NotNull
    private double fats;

    @NotNull
    private double proteins;

    @NotNull
    private double carbohydrates;

    public FoodResponse(Food food){
        this.id = food.getId();
        this.quantity = food.getQuantity();
        this.productId = food.getProduct().getId();
        this.userId = food.getUser().getId();
        this.calories = food.getCalories();
        this.fats = food.getFats();
        this.carbohydrates = food.getCarbohydrates();
        this.proteins = food.getProteins();
        this.name = food.getName();
        this.date = food.getDate();
    }
}
