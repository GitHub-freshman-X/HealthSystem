package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nutrient {
    private int nutrientId;
    private String name;
    private String unit;
    private String functionDesc;
    private String targetBenefits;
}
