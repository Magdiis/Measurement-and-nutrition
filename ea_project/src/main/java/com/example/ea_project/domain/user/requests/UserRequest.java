package com.example.ea_project.domain.user.requests;

import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.utils.enums.PhysicalActivity;
import com.example.ea_project.utils.enums.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Schema(example = "180")
    @NotNull
    @Min(0)
    private Integer height;

    @Schema(example = "easy")
    @NotNull
    @Enumerated(EnumType.STRING)
    private PhysicalActivity physicalActivity;

    @Schema(example = "Niki")
    @NotEmpty
    private String username;

    @Schema(example = "woman")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Schema(example = "50")
    @NotNull
    @Min(13)
    private Integer age;

    public void toUser(User user){
        user.setHeight(height);
        user.setPhysicalActivity(physicalActivity);
        user.setUsername(username);
        user.setAge(age);
        user.setSex(sex);
    }
}
