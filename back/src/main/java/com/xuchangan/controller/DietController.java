package com.xuchangan.controller;

import com.xuchangan.pojo.*;
import com.xuchangan.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/diet")
public class DietController {

    @Autowired
    private DietService dietService;

    // 获取每顿饭吃了啥
    @PostMapping("/getDietFoods")
    public Result<PageBean<DietFoodList>> getDietFoods(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false)LocalDate dietDate,
            @RequestParam(required = false)String mealType
    ){
        PageBean<DietFoodList> result = dietService.getDietFoods(pageNum, pageSize, dietDate, mealType);
        return Result.success(result);
    }

    // 查询当前用户具体某一天的每顿饭摄入的热量
    @PostMapping("/getDietCalories")
    public Result<List<DietFoodList>> getDietCalories(
            @RequestParam LocalDate dietDate
    ){
        List<DietFoodList> result = dietService.getDietCalories(dietDate);
        return Result.success(result);
    }

    // 上传
    @PostMapping("/uploadDietFoods")
    public Result uploadDietFoods(@RequestBody DietFoodList dietFoodList){
        dietService.uploadDietFoods(dietFoodList);
        return Result.success();
    }

    // 日记查看
    @PostMapping("/diary")
    public Result<PageBean<DietExerciseDiary>> getDietExerciseDiary(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) LocalDate diaryDate
    ){
        PageBean<DietExerciseDiary> result = dietService.getDietExerciseDiary(pageNum, pageSize, diaryDate);
        return Result.success(result);
    }

    // 上传日记
    @PostMapping("/uploadDiary")
    public Result uploadDiary(
            @RequestBody DietExerciseDiary dietExerciseDiary
    ){
        dietService.uploadDiary(dietExerciseDiary);
        return Result.success();
    }

    // 统计某顿饭各种营养素的摄入
    @PostMapping("/nutrientIntake")
    public Result<PageBean<DietNutrientIntake>> getDietNutrientIntake(
            Integer pageNum,
            Integer pageSize,
            @RequestParam LocalDate dietDate,
            @RequestParam(required = false) String mealType
    ){
        PageBean<DietNutrientIntake> result = dietService.getDietNutrientIntake(pageNum, pageSize, dietDate, mealType);
        return Result.success(result);
    }

    // 获取该年龄阶段各营养素推荐值
    @PostMapping("/nutrientSufficient")
    public Result<List<NutrientSufficient>> getNutrientSufficient(
            @RequestParam String gender,
            @RequestParam Integer age,
            @RequestParam LocalDate dietDate,
            @RequestParam(required = false) String pregnancy,
            @RequestParam(required = false) String lactation
    ){
        List<NutrientSufficient> result = dietService.getNutrientSufficient(gender, age, dietDate, pregnancy, lactation);
        return Result.success(result);
    }

    // 推荐营养餐
    @PostMapping("/nutritiousMeal")
    public Result<List<NutrientRec>> getNutritiousMeal(
            @RequestParam String gender,
            @RequestParam Integer age
    ){
        List<NutrientRec> result = dietService.getNutritiousMeal(gender, age);
        return Result.success(result);
    }

}
