package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diet {
    private Integer dietId;
    private Integer userId;
    private LocalDate dietDate;
    private String mealType;
}
