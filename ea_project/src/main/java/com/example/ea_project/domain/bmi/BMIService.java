package com.example.ea_project.domain.bmi;

import com.example.ea_project.utils.enums.DescriptionBMI;
import org.springframework.stereotype.Service;

@Service
public class BMIService {

    public DescriptionBMI getDescriptionBMI(double bmi){
        if(bmi < 18.5 ){
            return DescriptionBMI.underweight;
        } else if (bmi > 25 ) {
            return DescriptionBMI.overweight;
        } else {
            return DescriptionBMI.normal;
        }
    }
}
