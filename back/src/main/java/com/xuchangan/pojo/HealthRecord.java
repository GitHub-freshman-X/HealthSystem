package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthRecord {
    private int recordId;
    private int userId;
    private LocalDate recordDate;
    private double weight;
    private double bodyWater;
    private double protein;
    private double fatMass;
//    private double bodyFat;
    private double muscleMass;
    private double bmr;
    private int visceralFat;
//    private double bmi;
}
