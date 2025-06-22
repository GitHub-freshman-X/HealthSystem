package com.xuchangan.service;

import com.xuchangan.pojo.*;

import java.time.LocalDate;
import java.util.List;

public interface DietService {
    PageBean<DietFoodList> getDietFoods(Integer pageNum, Integer pageSize, LocalDate dietDate, String mealType);

    void uploadDietFoods(DietFoodList dietFoodList);

    PageBean<DietExerciseDiary> getDietExerciseDiary(Integer pageNum, Integer pageSize, LocalDate diaryDate);

    void uploadDiary(DietExerciseDiary dietExerciseDiary);

    PageBean<DietNutrientIntake> getDietNutrientIntake(Integer pageNum, Integer pageSize, LocalDate dietDate, String mealType);

    List<NutrientSufficient> getNutrientSufficient(String gender, Integer age, LocalDate dietDate, String pregnancy, String lactation);

    List<DietFoodList> getDietCalories(LocalDate dietDate);
}
