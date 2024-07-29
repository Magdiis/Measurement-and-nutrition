package com.example.ea_project.domain.food;

import com.example.ea_project.domain.food.models.Food;
import com.example.ea_project.domain.food.models.FoodDayInfo;
import com.example.ea_project.domain.food.requests.FoodRequest;
import com.example.ea_project.domain.food.responses.FoodDayInfoResponse;
import com.example.ea_project.domain.food.responses.FoodResponse;
import com.example.ea_project.domain.product.ProductService;
import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.domain.user.UserService;
import com.example.ea_project.utils.exceptions.NotFoundException;
import com.example.ea_project.utils.exceptions.ValidatingException;
import com.example.ea_project.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(
        name = "Food",
        description = "Management of food."
)
@RestController
@RequestMapping("food")
@Validated
public class FoodController {
    private final FoodService foodService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public FoodController(FoodService foodService, ProductService productService, UserService userService) {
        this.foodService = foodService;
        this.productService = productService;
        this.userService = userService;
    }

    @Operation(
            summary = "Add food and its quantity. Also date when you ate it."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Food created."),
            @ApiResponse(responseCode = "404", description = "Wrong user id or id of food.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Wrong quantity.", content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<FoodResponse> createFood(@RequestBody @Valid FoodRequest foodRequest) {
        Food food = new Food();
        foodRequest.toFood(food, userService, productService, foodService);
        Food createdFood = foodService.createFood(food);
        return ObjectResponse.of(createdFood, FoodResponse::new);
    }


    @Operation(
            summary = "Get information about day. How many calories, fats, proteins and carbohydrates you ate in total."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get day info."),
            @ApiResponse(responseCode = "400", description = "Bad date format or missing user id", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Wrong user id.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Future date.", content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping(value = "/dayInfo", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Valid
    public ObjectResponse<FoodDayInfoResponse> getDayInfo(@RequestParam UUID userId, @RequestParam LocalDate date) {
        User user = userService.getUserById(userId).orElseThrow(NotFoundException::new);
        // check date
        if(foodService.isDateInFuture(date)){
            throw new ValidatingException();
        } else {
            FoodDayInfo foodDayInfo = new FoodDayInfo();
            // get from DB
            List<Food> filteredFoods = foodService.getFilteredFoodsByDate(date, user);
            // set date and username
            foodDayInfo.setDate(date);
            foodDayInfo.setUsername(user.getUsername());
            //  calculate totals
            foodService.calculateMacronutrientInTotal(filteredFoods, foodDayInfo);
            return ObjectResponse.of(foodDayInfo, FoodDayInfoResponse::new);
        }
    }
}