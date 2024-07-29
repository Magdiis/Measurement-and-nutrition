package com.example.ea_project.domain.food.models;

import com.example.ea_project.domain.product.Product;
import com.example.ea_project.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    // grams
    @NotNull
    private double quantity;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    @ManyToOne
    private User user;

    @Transient
    @NotNull
    private String name;

    @Transient
    @NotNull
    private double calories;

    @Transient
    @NotNull
    private double fats;

    @Transient
    @NotNull
    private double proteins;

    @Transient
    @NotNull
    private double carbohydrates;
}
