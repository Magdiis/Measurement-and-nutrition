package com.example.ea_project.domain.measurement;

import com.example.ea_project.domain.user.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeasurementService {
    private MeasurementRepository repository;

    public MeasurementService(MeasurementRepository repository){
        this.repository = repository;
    }

    public Measurement createMeasurement(Measurement measurement){
        return repository.save(measurement);
    }

    public List<Measurement> getMeasurementsFilteredByDate(User user, LocalDate startDate, LocalDate endDate){
        List<Measurement> measurements = new ArrayList<>();
        repository.findAllByUserAndDateBetween(user, startDate, endDate).forEach(measurements::add);
        return measurements;
    }

    public void deleteMeasurement(Long id){
        repository.deleteById(id);
    }
}
