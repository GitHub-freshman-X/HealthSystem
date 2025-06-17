package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHealthView {
    // 用户基础信息
    private Integer userId;
    private String username;
    private String realName;
    private String gender;
    private Integer age;
    private Double height;

    // 健康记录相关
    private LocalDate recordDate;
    private Double weight;
    private Double bodyWater;
    private Double protein;
    private Double fatMass;
    private Double muscleMass;
    private Double bmr;
    private Double visceralFat;

    // 派生字段
    private Double bodyFatRate;  // 体脂率 (%)
    private Double bmi;          // 身体质量指数
}
