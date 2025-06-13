package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientStandard {
    private int standardId;
    private int nutrientId;
    private String gender;
    private int ageMin;
    private int ageMax;
    private Boolean pregnancy;
    private Boolean lactation;
    private double recommendAmount;
    private String unit;
}
