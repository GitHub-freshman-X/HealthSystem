package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientRec {
    private int recId;
    private int userId;
    private LocalDateTime recDate;
    private int nutrientId;
    private int foodId;
    private String recType;
    private double recAmount;
    private String reason;
}
