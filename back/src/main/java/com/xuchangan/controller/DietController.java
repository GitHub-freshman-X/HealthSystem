package com.xuchangan.controller;

import com.xuchangan.pojo.DietFoodList;
import com.xuchangan.pojo.Result;
import com.xuchangan.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/diet")
public class DietController {

    @Autowired
    private DietService dietService;

    // 获取每顿饭吃了啥
    @GetMapping("/getDietFoods")
    public Result<List<DietFoodList>> getDietFoods(){
        List<DietFoodList> result = dietService.getDietFoods();
        return Result.success(result);
    }


}
