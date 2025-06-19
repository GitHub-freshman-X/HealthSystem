package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietFoodView {

    private String realName;
    private LocalDate dietDate;
    private String mealType;
    private String foodName;
    private Double quantity;
    private String imagePath;

}
