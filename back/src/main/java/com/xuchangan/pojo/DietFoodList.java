package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietFoodList {

    private String realName;
    private LocalDate dietDate;
    private String mealType;

    List<String> foodList;
    List<Double> quantityList;
    List<String> imagePathList;

}
