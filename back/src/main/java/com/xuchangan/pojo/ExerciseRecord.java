package com.xuchangan.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRecord {
    private int exerciseId;
    private int userId;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate exerciseDate;
    private String exerciseType;
    private int duration;
    private int calorieBurned;
    private int steps;
}
