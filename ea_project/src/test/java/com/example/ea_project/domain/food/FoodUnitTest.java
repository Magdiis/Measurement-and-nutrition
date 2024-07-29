package com.example.ea_project.domain.food;

import com.example.ea_project.domain.food.models.Food;
import com.example.ea_project.domain.food.models.FoodDayInfo;
import com.example.ea_project.domain.product.Product;
import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.utils.enums.Macronutrient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FoodUnitTest {
    // CALCULATE MACRONUTRIENT
    @Test
    void testFromProductCalculateMacronutrient(){
        Product product = new Product("someProduct",150,100,50,20);
        FoodService foodService = new FoodService(null);

        // 100g
        assertThat(foodService.calculate(product,100, Macronutrient.calories),is(150.0));
        assertThat(foodService.calculate(product,100, Macronutrient.fats),is(100.0));
        assertThat(foodService.calculate(product,100, Macronutrient.proteins),is(50.0));
        assertThat(foodService.calculate(product,100, Macronutrient.carbohydrates),is(20.0));

        // 0g
        assertThat(foodService.calculate(product,0, Macronutrient.calories),is(0.0));
        assertThat(foodService.calculate(product,0, Macronutrient.fats),is(0.0));
        assertThat(foodService.calculate(product,0, Macronutrient.proteins),is(0.0));
        assertThat(foodService.calculate(product,0, Macronutrient.carbohydrates),is(0.0));

        // 50g
        assertThat(foodService.calculate(product,50, Macronutrient.calories),is(75.0));
        assertThat(foodService.calculate(product,50, Macronutrient.fats),is(50.0));
        assertThat(foodService.calculate(product,50, Macronutrient.proteins),is(25.0));
        assertThat(foodService.calculate(product,50, Macronutrient.carbohydrates),is(10.0));

        // 1g
        assertThat(foodService.calculate(product,1, Macronutrient.calories),is(1.5));
        assertThat(foodService.calculate(product,1, Macronutrient.fats),is(1.0));
        assertThat(foodService.calculate(product,1, Macronutrient.proteins),is(0.5));
        assertThat(foodService.calculate(product,1, Macronutrient.carbohydrates),is(0.2));

    }

    @Test
    void testCalculateMacronutrientsInTotal(){
        FoodService foodService = new FoodService(null);
        User user = new User();
        Product product = new Product();
        FoodDayInfo foodDayInfo = new FoodDayInfo();
        List<Food> foodList =Arrays.asList(
                new Food(1L, LocalDate.now(),100,product,user,
                        "someProduct",100,50.5,10.2,0),
                new Food(1L, LocalDate.now(),100,product,user,
                        "someProduct",100,50,10,0),
                new Food(1L, LocalDate.now(),100,product,user,
                        "someProduct",100.56,50,10,0),
                new Food(1L, LocalDate.now(),100,product,user,
                        "someProduct",100,50,10,0)

        ) ;

        foodService.calculateMacronutrientInTotal(foodList, foodDayInfo);

        assertThat(foodDayInfo.getCaloriesInTotal(),is(400.56));
        assertThat(foodDayInfo.getFatsInTotal(),is(200.5));
        assertThat(foodDayInfo.getProteinsInTotal(),is(40.2));
        assertThat(foodDayInfo.getCarbohydratesInTotal(),is(0.0));

    }
}
