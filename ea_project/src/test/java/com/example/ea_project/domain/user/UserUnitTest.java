package com.example.ea_project.domain.user;

import com.example.ea_project.domain.bmi.BMI;
import com.example.ea_project.domain.bmi.BMIService;
import com.example.ea_project.domain.measurement.Measurement;
import com.example.ea_project.domain.measurement.MeasurementRepository;
import com.example.ea_project.domain.measurement.MeasurementService;
import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.utils.enums.DescriptionBMI;
import com.example.ea_project.utils.enums.Macronutrient;
import com.example.ea_project.utils.enums.PhysicalActivity;
import com.example.ea_project.utils.enums.Sex;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserUnitTest {
    // CALCULATE AVERAGE WEIGHT
    @Test
    void testAverageWeight(){
        User user = new User();
        List<Measurement> measurementList = List.of(
                new Measurement(1L,LocalDate.now(),80.0,user),
                new Measurement(1L,LocalDate.now(),83.12,user),
                new Measurement(1L,LocalDate.now(),87.4,user),
                new Measurement(1L,LocalDate.now(),81.5,user),
                new Measurement(1L,LocalDate.now(),86.7,user),
                new Measurement(1L,LocalDate.now(),82.4,user),
                new Measurement(1L,LocalDate.now(),86.7,user),
                new Measurement(1L,LocalDate.now(),80.2,user)
        );
        UserService userService = new UserService(null);

        assertThat(userService.countAverageWeight(measurementList),is(83.5025));
    }


    // AVERAGE BMI IN SELECTED MONTH
    @Test
    void testGetAverageBMIInMonth(){
        UUID id = UUID.randomUUID();
        UserRepository userRepository = mock(UserRepository.class);
        User user = new User(
                id,180,"name",
                PhysicalActivity.normal, Sex.man,30,new HashSet<>(),new HashSet<>());

                when(userRepository.findById(id)).thenReturn(
                Optional.of(user));


        UserService userService = new UserService(userRepository);
        MeasurementService measurementService = mock(MeasurementService.class);
        when(measurementService.getMeasurementsFilteredByDate(user, LocalDate.of(2023,1,1),
                LocalDate.of(2023,1,31)))
                .thenReturn(List.of(
                        new Measurement(1L,LocalDate.of(2023,1,1),80, user),
                        new Measurement(1L,LocalDate.of(2023,1,2),81, user),
                        new Measurement(1L,LocalDate.of(2023,1,3),82, user)
                ));
        BMIService bmiService = new BMIService();

        BMI bmi = userService.getAverageBMIInMonth(2023,1, id,measurementService,bmiService);

        assertThat(bmi.getAverageBMI(),is(25.0));
        assertThat(bmi.getAverageWeight(),is(81.0));
        assertThat(bmi.getDescriptionBMI(),is(DescriptionBMI.normal));

    }




    // CALCULATE BASAL METABOLISM (MAN, WOMAN)
    @Test
    void testBasalMetabolism(){
        UserService userService = new UserService(null);

        User man = new User(UUID.randomUUID(),180,"man",PhysicalActivity.easy,Sex.man,30,new HashSet<>(), new HashSet<>());

        assertThat(userService.calculateBasalMetabolism(man,80),is(1780.0));
        assertThat(userService.calculateBasalMetabolism(man,75),is(1730.0));
        assertThat(userService.calculateBasalMetabolism(man,85),is(1830.0));

        User woman =  new User(UUID.randomUUID(),170,"woman",PhysicalActivity.easy,Sex.woman,30,new HashSet<>(), new HashSet<>());
        assertThat(userService.calculateBasalMetabolism(woman,80),is(1552.0));
        assertThat(userService.calculateBasalMetabolism(woman,75),is(1502.0));
        assertThat(userService.calculateBasalMetabolism(woman,85),is(1602.0));
    }

    // CALCULATE RECOMMENDED MACRONUTRIENTS
    @Test
    void testRecommendedMacronutrients(){
        UserService userService = new UserService(null);
        assertThat(userService.calculateRecommendedMacronutrients(1000, Macronutrient.calories),is(1000.0));
        assertThat(userService.calculateRecommendedMacronutrients(1000, Macronutrient.carbohydrates),is(125.0));
        assertThat(userService.calculateRecommendedMacronutrients(1000, Macronutrient.fats),is(27.78));
        assertThat(userService.calculateRecommendedMacronutrients(1000, Macronutrient.proteins),is(62.5));
    }

}
