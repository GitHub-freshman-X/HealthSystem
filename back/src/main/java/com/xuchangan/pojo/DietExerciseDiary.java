package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietExerciseDiary {
    private int dietId;
    private int userId;
    private LocalDate diaryDate;
    private double waterIntake;
    private String content;
}
