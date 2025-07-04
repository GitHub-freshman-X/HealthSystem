package com.xuchangan.service.impl;

import com.xuchangan.mapper.NutrientMapper;
import com.xuchangan.pojo.FoodNutrient;
import com.xuchangan.pojo.Nutrient;
import com.xuchangan.pojo.NutrientRecommendDTO;
import com.xuchangan.service.NutrientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NutrientServiceImpl implements NutrientService {

    @Autowired
    private NutrientMapper nutrientMapper;

    @Override
    public List<Nutrient> list(String nutrientName) {
        return nutrientMapper.list(nutrientName);
    }

    @Override
    public void add(Nutrient nutrient) {
        nutrientMapper.add(nutrient);
    }

    @Override
    public void delete(Integer nutrientId) {
        nutrientMapper.delete(nutrientId);
    }

    @Override
    public void update(Nutrient nutrient) {
        nutrientMapper.update(nutrient);
    }

    @Override
    public List<NutrientRecommendDTO> getSupplyRecommend(Integer age, String gender, Double height, Double weight) {
        List<NutrientRecommendDTO> recommendList = nutrientMapper.getSupplyRecommend(age, gender, height, weight);
        return recommendList;
    }

    @Override
    public List<FoodNutrient> getFoodNutrient(String foodName, String nutrientName) {
        List<FoodNutrient> list = nutrientMapper.getFoodNutrient(foodName, nutrientName);
        return list;
    }

    @Override
    public List<FoodNutrient> getFoodByNutrient(String nutrientName) {
        List<FoodNutrient> list = nutrientMapper.getFoodByNutrient(nutrientName);
        List<FoodNutrient> filteredList = list.stream()
                .filter(foodNutrient -> foodNutrient.getAmount() > 0)
                .collect(Collectors.toList());
        return filteredList;
    }

    @Override
    public List<FoodNutrient> getNutrientsByFunction(String functionKeyword) {
        List<String> nutrients = nutrientMapper.getNutrientsByFunction(functionKeyword);

        List<FoodNutrient> resultList = new ArrayList<>();
        for(String nutrientName: nutrients){
            List<FoodNutrient> list = nutrientMapper.getFoodByNutrient(nutrientName);
            if (list != null && !list.isEmpty()) {
                resultList.addAll(list);
            }
        }

        return resultList;
    }
}
