package com.xuchangan.controller;

import com.xuchangan.pojo.*;
import com.xuchangan.service.NutrientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nutrient")
public class NutrientController {

    @Autowired
    private NutrientService nutrientService;

    @PostMapping("list")
    public Result<List<Nutrient>> list(
            @RequestParam(required = false) String nutrientName
    ){
        List<Nutrient> result = nutrientService.list(nutrientName);
        return Result.success(result);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Nutrient nutrient){
        nutrientService.add(nutrient);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete (@RequestParam Integer nutrientId) {
        nutrientService.delete(nutrientId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Nutrient nutrient) {
        nutrientService.update(nutrient);
        return Result.success();
    }

    // 根据年龄、性别、身高、体重查营养素供给量
    @PostMapping("/supplyRecommend")
    public Result<List<NutrientRecommendDTO>> getSupplyRecommend(
            @RequestParam Integer age,
            @RequestParam String gender,
            @RequestParam Double height,
            @RequestParam Double weight
    ){
        List<NutrientRecommendDTO> result = nutrientService.getSupplyRecommend(age, gender, height, weight);
        return Result.success(result);
    }

    // 查询食物所包含的营养素
    @PostMapping("/foodNutrient")
    public Result<List<FoodNutrient>> getFoodNutrient(
            @RequestParam String foodName,
            @RequestParam(required = false) String nutrientName
    ){
        List<FoodNutrient> result = nutrientService.getFoodNutrient(foodName, nutrientName);
        return Result.success(result);
    }

    // 查询包含指定营养素的食物
    @PostMapping("/foodByNutrient")
    public Result<List<FoodNutrient>> getFoodByNutrient(
            @RequestParam String nutrientName
    ){
        List<FoodNutrient> result = nutrientService.getFoodByNutrient(nutrientName);
        return Result.success(result);
    }

}
