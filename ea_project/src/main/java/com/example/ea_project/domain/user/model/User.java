package com.example.ea_project.domain.user.model;

import com.example.ea_project.domain.food.models.Food;
import com.example.ea_project.domain.measurement.Measurement;
import com.example.ea_project.utils.enums.PhysicalActivity;
import com.example.ea_project.utils.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // cm
    @NotNull
    @Min(0)
    private Integer height;

    @NotEmpty
    private String username;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PhysicalActivity physicalActivity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @NotNull
    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Measurement> measurements = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Food> foods = new HashSet<>();

    public User(Integer height, String username, PhysicalActivity physicalActivity, Integer age, Sex sex){
        this.height = height;
        this.username  = username;
        this.physicalActivity = physicalActivity;
        this.age = age;
        this.sex = sex;
    }

}

