package com.example.ea_project.domain.food;

import com.example.ea_project.domain.food.models.Food;
import com.example.ea_project.domain.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface FoodRepository extends CrudRepository<Food, Long> {
    Iterable<Food> findAllByUserAndDateEquals(User user, LocalDate localDate);

}
