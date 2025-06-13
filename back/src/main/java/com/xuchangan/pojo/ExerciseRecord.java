package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRecord {
    private int exerciseId;
    private int userId;
    private LocalDateTime exerciseDate;
    private String exerciseType;
    private int duration;
    private int calorieBurned;
    private int steps;
}
