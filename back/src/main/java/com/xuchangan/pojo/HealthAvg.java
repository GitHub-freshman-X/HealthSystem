package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthAvg {
    private Integer id;

    // 性别（如"男"、"女"）
    private String gender;

    // 年龄区间
    private Integer ageMin;
    private Integer ageMax;

    // 各项平均指标
    private Double avgHeight;
    private Double avgWeight;
    private Double avgBodyWater;
    private Double avgProtein;
    private Double avgFatMass;
    private Double avgMuscleMass;
    private Double avgBmr;
    private Double avgVisceralFat;
    private Double avgBodyFatRate;
    private Double avgBmi;
}
