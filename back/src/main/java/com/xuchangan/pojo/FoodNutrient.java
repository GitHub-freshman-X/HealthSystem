package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodNutrient {
    private int foodId;
    private int nutrientId;
    private String foodName;
    private String nutrientName;
    private double amount;
}
