package com.example.ea_project.domain.user.responses;

import com.example.ea_project.domain.food.models.Food;
import com.example.ea_project.domain.measurement.Measurement;
import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.utils.enums.PhysicalActivity;
import com.example.ea_project.utils.enums.Sex;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {
    @NotNull
    private UUID id;

    @NotEmpty
    private String username;

    @NotNull
    @Min(0)
    private Integer height;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PhysicalActivity physicalActivity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @NotNull
    private Integer age;

    List<Long> measurementIDs;

    List<Long> foodIds;

    public UserResponse(User user){
        this.id = user.getId();
        this.height = user.getHeight();
        this.physicalActivity = user.getPhysicalActivity();
        this.measurementIDs = user.getMeasurements().stream().map(Measurement::getId).toList();
        this.username = user.getUsername();
        this.foodIds = user.getFoods().stream().map(Food::getId).toList();
        this.sex = user.getSex();
        this.age = user.getAge();
    }

}
