package com.example.ea_project.domain.bmi;

import com.example.ea_project.utils.enums.DescriptionBMI;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BMIResponse {
    @NotNull
    @Min(0)
    private double averageWeight;

    @NotNull
    @Min(0)
    private double averageBMI;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DescriptionBMI descriptionBMI;

    public BMIResponse(BMI bmi){
        this.averageWeight = bmi.getAverageWeight();
        this.averageBMI = bmi.getAverageBMI();
        this.descriptionBMI = bmi.getDescriptionBMI();
    }
}
