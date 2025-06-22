package com.xuchangan.service;

import com.xuchangan.pojo.FoodNutrient;
import com.xuchangan.pojo.Nutrient;
import com.xuchangan.pojo.NutrientRecommendDTO;

import java.util.List;

public interface NutrientService {
    // 查询所有营养素数据
    List<Nutrient> list(String nutrientName);

    // 添加营养素
    void add(Nutrient nutrient);

    // 根据ID删除营养素
    void delete(Integer nutrientId);

    // 更新
    void update(Nutrient nutrient);

    List<NutrientRecommendDTO> getSupplyRecommend(Integer age, String gender, Double height, Double weight);

    List<FoodNutrient> getFoodNutrient(String foodName, String nutrientName);

    List<FoodNutrient> getFoodByNutrient(String nutrientName);
}
