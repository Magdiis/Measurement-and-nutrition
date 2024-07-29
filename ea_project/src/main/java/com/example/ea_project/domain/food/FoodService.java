package com.example.ea_project.domain.food;

import com.example.ea_project.domain.food.models.Food;
import com.example.ea_project.domain.food.models.FoodDayInfo;
import com.example.ea_project.domain.product.Product;
import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.utils.enums.Macronutrient;
import com.example.ea_project.utils.exceptions.ValidatingException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {
    private FoodRepository repository;

    public FoodService(FoodRepository foodRepository){
        this.repository = foodRepository;
    }

    // 1
    public double calculate(Product product, double quantity, Macronutrient macronutrient) throws RuntimeException{
        switch (macronutrient){
            case fats -> {
               // bmi = Math.round(bmi * 100.0) / 100.0;
                return Math.round(((quantity/100)*product.getFats())*100)/100.0;
            }
            case proteins -> {
                return Math.round(((quantity/100)*product.getProteins())*100)/100.0;
            }
            case carbohydrates -> {
                return Math.round(((quantity/100)*product.getCarbohydrates())*100)/100.0;
            }
            case calories -> {
                return Math.round(((quantity/100)*product.getCalories())*100)/100.0;
            }
            default -> {
                throw new ValidatingException();
            }
        }
    }

    public Food createFood(Food food){
        return repository.save(food);
    }

    public List<Food> getFilteredFoodsByDate(LocalDate date, User user){
        // GET FILTERED FOOD FROM DATABASE
        List<Food> foods = new ArrayList<>();
        repository.findAllByUserAndDateEquals(user, date).forEach(foods::add);
        // SETTING NAME FROM PRODUCT. CALCULATING CALORIES, FATS, PROTEINS, CARBOHYDRATES
        foods.forEach(this::settingFoodAttributes);
        return foods;
    }

    private void settingFoodAttributes(Food food){
        // calories, fats, proteins, carbohydrates
        food.setCalories(calculate(food.getProduct(),food.getQuantity(),Macronutrient.calories));
        food.setFats(calculate(food.getProduct(),food.getQuantity(),Macronutrient.fats));
        food.setProteins(calculate(food.getProduct(),food.getQuantity(),Macronutrient.proteins));
        food.setCarbohydrates(calculate(food.getProduct(),food.getQuantity(),Macronutrient.carbohydrates));
    }



    // 2
    public void calculateMacronutrientInTotal(List<Food> foodList, FoodDayInfo foodDayInfo){
        foodDayInfo.setCarbohydratesInTotal(foodList.stream().mapToDouble(Food::getCarbohydrates).sum());
        foodDayInfo.setCaloriesInTotal(foodList.stream().mapToDouble(Food::getCalories).sum());
        foodDayInfo.setProteinsInTotal(foodList.stream().mapToDouble(Food::getProteins).sum());
        foodDayInfo.setFatsInTotal(foodList.stream().mapToDouble(Food::getFats).sum());
    }

    public boolean isDateInFuture(LocalDate date){
        LocalDate now = LocalDate.now();
        return now.isBefore(date);
    }

}
