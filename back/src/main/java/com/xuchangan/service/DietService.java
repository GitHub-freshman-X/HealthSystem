package com.xuchangan.service;

import com.xuchangan.pojo.DietFoodList;
import com.xuchangan.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface DietService {
    PageBean<DietFoodList> getDietFoods(Integer pageNum, Integer pageSize, LocalDate dietDate, String mealType);

    void uploadDietFoods(DietFoodList dietFoodList);
}
