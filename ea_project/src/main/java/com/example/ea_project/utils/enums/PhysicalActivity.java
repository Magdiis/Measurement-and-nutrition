package com.example.ea_project.utils.enums;

public enum PhysicalActivity {
    easy("little movement",1.2),
    normal("2 exercises per week",1.4),
    hard("3-4 exercises per week",1.6),
    intense("5-6 exercises per week",1.9);

    private final double coefficient;
    private final String description;

    PhysicalActivity(String description,double coefficient) {
        this.description = description;
        this.coefficient = coefficient;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public String getDescription() {
        return description;
    }
}