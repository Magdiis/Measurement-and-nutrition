package com.example.ea_project.domain.product;

import com.example.ea_project.domain.food.models.Food;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer calories;

    @NotNull
    private double fats;

    @NotNull
    private double proteins;

    @NotNull
    private double carbohydrates;

    @OneToMany(mappedBy = "product")
    @EqualsAndHashCode.Exclude
    private Set<Food> foods = new HashSet<>();

    public Product(String name, Integer calories, double fats, double proteins, double carbohydrates){
        this.calories = calories;
        this.fats = fats;
        this.name = name;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
    }

}
