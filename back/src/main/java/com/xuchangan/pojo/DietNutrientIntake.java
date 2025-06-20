package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietNutrientIntake {

    private String realName;
    private LocalDate dietDate;
    private String mealType;
    private String nutrientName;
    private Double intakeAmount;

}
