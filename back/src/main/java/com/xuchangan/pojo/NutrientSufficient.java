package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientSufficient {

    private String realName;
    private LocalDate dietDate;
    private String nutrientName;
    private Double intakeAmount;
    private Double recommendAmount;
    private String unit;
    private String isSufficient;

}
