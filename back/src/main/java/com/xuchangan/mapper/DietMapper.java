package com.xuchangan.mapper;

import com.xuchangan.pojo.Diet;
import com.xuchangan.pojo.DietFoodView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DietMapper {
    List<DietFoodView> getDietFoods(Integer userId, LocalDate dietDate, String mealType);

    List<Diet> getDistinctMeals(Integer userId, LocalDate dietDate, String mealType);
}
