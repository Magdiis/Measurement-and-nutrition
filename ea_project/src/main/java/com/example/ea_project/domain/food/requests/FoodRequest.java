package com.example.ea_project.domain.food.requests;

import com.example.ea_project.domain.food.FoodService;
import com.example.ea_project.domain.food.models.Food;
import com.example.ea_project.domain.product.Product;
import com.example.ea_project.domain.product.ProductService;
import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.domain.user.UserService;
import com.example.ea_project.utils.enums.Macronutrient;
import com.example.ea_project.utils.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest {
    @Schema(example = "50.0")
    @NotNull
    @Min(0)
    private double quantity;

    @Schema(example = "2024-01-01")
    @NotNull
    private LocalDate date;

    @Schema(example = "1")
    @NotNull
    private Long productId;

    @Schema(example = "7acb1eb3-1472-4da6-bf0d-8ed838da7971")
    @NotNull
    private UUID userId;

    public void toFood(Food food, UserService userService, ProductService productService, FoodService foodService){
        User user = userService.getUserById(this.userId).orElseThrow(NotFoundException::new);
        Product product = productService.getProductById(this.productId).orElseThrow(NotFoundException::new);
        food.setDate(this.date);
        food.setUser(user);
        food.setProduct(product);
        food.setName(product.getName());
        food.setQuantity(this.quantity);
        food.setCalories(foodService.calculate(product,this.quantity, Macronutrient.calories));
        food.setFats(foodService.calculate(product,this.quantity, Macronutrient.fats));
        food.setProteins(foodService.calculate(product,this.quantity, Macronutrient.proteins));
        food.setCarbohydrates(foodService.calculate(product,this.quantity, Macronutrient.carbohydrates));
    }
}
