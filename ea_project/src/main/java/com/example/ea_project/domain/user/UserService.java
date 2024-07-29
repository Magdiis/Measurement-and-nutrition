package com.example.ea_project.domain.user;

import com.example.ea_project.domain.bmi.BMI;
import com.example.ea_project.domain.bmi.BMIService;
import com.example.ea_project.domain.measurement.Measurement;
import com.example.ea_project.domain.measurement.MeasurementService;
import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.domain.user.model.UserMetabolism;
import com.example.ea_project.utils.enums.Macronutrient;
import com.example.ea_project.utils.enums.PhysicalActivity;
import com.example.ea_project.utils.enums.Sex;
import com.example.ea_project.utils.exceptions.NotFoundException;
import com.example.ea_project.utils.exceptions.ValidatingException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public Optional<User> createUser(User user) {
        if (isUsernameUnique(user.getUsername())){
            return Optional.empty();
        } else {
            return Optional.of(repository.save(user));
        }
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add);
        return users;
    }

    public Optional<User> getUserById(UUID uuid){
        return repository.findById(uuid);
    }


    public void deleteUser(UUID id){
        repository.deleteById(id);
    }

    public User updateUser(UUID id, User user) {
        user.setId(id);
        return repository.save(user);
    }

    private boolean isUsernameUnique(String username){
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add); // must convert iterable to list
        List<User> filteredUsers = users.stream().filter(user -> user.getUsername().equals(username)).toList();
        return !filteredUsers.isEmpty();
    }

    // 3
    public BMI getAverageBMIInMonth(Integer year, Integer month, UUID id, MeasurementService measurementService, BMIService bmiService) throws RuntimeException {
        if(isYearAndMonthValidated(year,month)){
            User user = getUserById(id).orElseThrow(NotFoundException::new);
            List<Measurement> measurements = getMeasurementsFilteredByYearAndMonth(user, year, month, measurementService);
            double averageWeight = countAverageWeight(measurements);
            double heightMeter = (double) user.getHeight() /100;
            double bmi = averageWeight/(heightMeter*heightMeter);
            bmi = Math.round(bmi * 100.0) / 100.0;
            return new BMI(averageWeight, bmi,bmiService.getDescriptionBMI(bmi));
        } else {
            throw new ValidatingException();
        }
    }


    private boolean isYearAndMonthValidated(Integer year, Integer month){
        LocalDate now = LocalDate.now();
        return now.getYear() >= year && (month <= 12 && month >= 1);
    }

    private Integer calculateLastDayOfMonth(Integer year, Integer month){
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    private List<Measurement> getMeasurementsFilteredByYearAndMonth(User user, Integer year, Integer month, MeasurementService measurementService){
        LocalDate startDate = LocalDate.of(year,month,1);
        LocalDate endDate = LocalDate.of(year, month, calculateLastDayOfMonth(year,month));
        return measurementService.getMeasurementsFilteredByDate(user,startDate,endDate);
    }

    // 4
    public double countAverageWeight(List<Measurement> measurements){
        if(!measurements.isEmpty()){
            double sum = measurements.stream().mapToDouble(Measurement::getWeight).sum();
            return sum/measurements.size();
        } else {
            return 0.0;
        }
    }

    // user metabolism

    //5
    public double calculateBasalMetabolism(User user, Integer currentWeight){
        switch (user.getSex()){
            case woman -> {
                return Math.round(10 * currentWeight + 6.25 * user.getHeight() - 5 * user.getAge() - 161) ;
            }
            case man -> {
               return Math.round( 10 * currentWeight + 6.25 * user.getHeight() - 5 * user.getAge() + 5);
            }
            default -> {
                throw new ValidatingException();
            }
        }
    }


    public boolean isWeightOver20(Integer currentWeight){
        // for example
        return currentWeight > 20;
    }

    public void calculateRecommendedMacronutrients(UserMetabolism userMetabolism, User user){
        double recommendedCalorieIntake = calculateCalorieIntake(userMetabolism.getBasalMetabolism(),user.getPhysicalActivity());
        if (recommendedCalorieIntake<0){
            userMetabolism.setRecommendedCalorieIntake(0);
            userMetabolism.setRecommendedFats(0);
            userMetabolism.setRecommendedProteins(0);
            userMetabolism.setRecommendedCarbohydrates(0);
        } else {
            userMetabolism.setRecommendedCalorieIntake(recommendedCalorieIntake);
            userMetabolism.setRecommendedFats(calculateRecommendedMacronutrients(recommendedCalorieIntake,Macronutrient.fats));
            userMetabolism.setRecommendedProteins(calculateRecommendedMacronutrients(recommendedCalorieIntake,Macronutrient.proteins));
            userMetabolism.setRecommendedCarbohydrates(calculateRecommendedMacronutrients(recommendedCalorieIntake,Macronutrient.carbohydrates));
        }
    }
    private double calculateCalorieIntake(double basalMetabolism, PhysicalActivity physicalActivity){
        // TODO: assert  that

        return Math.round((basalMetabolism * physicalActivity.getCoefficient() * 0.85) * 100.0) / 100.0;
    }

    //6
    public double calculateRecommendedMacronutrients(double calorieIntake, Macronutrient macronutrient){
        // carbo : protein : fats = 50 : 25 : 25
        double halfCalorieIntake = Math.round(calorieIntake/2);
        double quarterCalorieIntake = Math.round(calorieIntake/4);
        switch (macronutrient){
            // carbo 1g = 4kcal
            case carbohydrates -> {
                return Math.round((halfCalorieIntake/4) * 100.0) / 100.0;
            }
            //protein 1g = 4kcal
            case proteins -> {
                return Math.round((quarterCalorieIntake/4) * 100.0) / 100.0;
            }
            //fats 1g = 9kcal
            case fats -> {
                return Math.round((quarterCalorieIntake/9) * 100.0) / 100.0;
            }
            case calories -> {
                return calorieIntake;
            }
            default -> {
                throw new ValidatingException();
            }
        }
    }


}
