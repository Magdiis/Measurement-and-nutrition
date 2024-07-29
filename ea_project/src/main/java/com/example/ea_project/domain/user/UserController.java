package com.example.ea_project.domain.user;

import com.example.ea_project.domain.bmi.BMI;
import com.example.ea_project.domain.bmi.BMIResponse;
import com.example.ea_project.domain.bmi.BMIService;
import com.example.ea_project.domain.measurement.MeasurementService;
import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.domain.user.model.UserMetabolism;
import com.example.ea_project.domain.user.requests.UserRequest;
import com.example.ea_project.domain.user.responses.UserMetabolismResponse;
import com.example.ea_project.domain.user.responses.UserResponse;
import com.example.ea_project.utils.exceptions.NotFoundException;
import com.example.ea_project.utils.exceptions.UniquenessException;
import com.example.ea_project.utils.exceptions.ValidatingException;
import com.example.ea_project.utils.response.ArrayResponse;
import com.example.ea_project.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(
        name = "Users",
        description = "Management of users."
)
@RestController
@RequestMapping("users")
@Validated
public class UserController {
    private final UserService userService;
    private final MeasurementService measurementService;

    private final BMIService bmiService;

    @Autowired
    public UserController(UserService userService, MeasurementService measurementService, BMIService bmiService){
        this.userService = userService;
        this.measurementService = measurementService;
        this.bmiService = bmiService;
    }

    @Operation(
            summary = "Create user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created."),
            @ApiResponse(responseCode = "409", description = "Username is not unique.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Height is under zero or username is empty or age is under 18.", content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping(value = "",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest){
        User user = new User();
        userRequest.toUser(user);
        User response = userService.createUser(user).orElseThrow(UniquenessException::new);
        return ObjectResponse.of(response, UserResponse::new);
    }

    @Operation(
            summary = "Get all users"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users")
    })
    @GetMapping(value = "", produces = "application/json")
    @Valid
    public ArrayResponse<UserResponse> getAllUsers(){
        return ArrayResponse.of(
                userService.getAllUsers(),
                UserResponse::new
        );
    }



    @Operation(
            summary = "Get average BMI of month of user. Also description what BMI index means."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Average BMI of month."),
            @ApiResponse(responseCode = "409", description = "The year or month is future.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "User not found. Wrong UUID", content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping(value = "/averageBMI/{id}/{year}/{month}", produces = "application/json")
    @Valid
    public ObjectResponse<BMIResponse> getAverageBMIForMonth(@PathVariable UUID id, @PathVariable Integer year, @PathVariable Integer month){
        BMI bmi = userService.getAverageBMIInMonth(year,month,id,measurementService, bmiService);
        return ObjectResponse.of(bmi, BMIResponse::new);
    }

    @Operation(
            summary = "Info about metabolism. Also recommended calories, fats, proteins and carbohydrates"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Metabolism info."),
            @ApiResponse(responseCode = "409", description = "Weight is too small,", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "User not found. Wrong UUID", content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping(value = "/metabolismInfo", produces = "application/json")
    @Valid
    public ObjectResponse<UserMetabolismResponse> getUserMetabolism(@RequestParam UUID userId, @RequestParam Integer currentWeight){
        User user = userService.getUserById(userId).orElseThrow(NotFoundException::new);
        if(!userService.isWeightOver20(currentWeight)){
            throw new ValidatingException();
        } else {
            UserMetabolism userMetabolism = new UserMetabolism();
            userMetabolism.setUsername(user.getUsername());
            userMetabolism.setCurrentWeight(currentWeight);
            userMetabolism.setBasalMetabolism(userService.calculateBasalMetabolism(user,currentWeight));
            userService.calculateRecommendedMacronutrients(userMetabolism,user);
            return ObjectResponse.of(userMetabolism,UserMetabolismResponse::new);
        }
    }

    @Operation(
            summary = "Delete user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted", content = @Content(schema = @Schema(hidden = true))),
    })
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
    }


    @Operation(
            summary = "Update user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User updated."),
            @ApiResponse(responseCode = "400", description = "Height is under zero or username is empty or age is under 18.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "User not found. Wrong UUID", content = @Content(schema = @Schema(hidden = true))),
    })
    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    @Transactional
    public ObjectResponse<UserResponse> updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequest userRequest){
        User user = userService.getUserById(id).orElseThrow(NotFoundException::new);
        userRequest.toUser(user);

        User updatedUser = userService.updateUser(id, user);
        return ObjectResponse.of(updatedUser, UserResponse::new);
    }

}
