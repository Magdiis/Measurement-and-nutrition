package com.example.ea_project.domain.measurement;

import com.example.ea_project.domain.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;


public interface MeasurementRepository extends CrudRepository<Measurement, Long> {
    Iterable<Measurement> findAllByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
