package com.xuchangan.controller;

import com.xuchangan.pojo.Nutrient;
import com.xuchangan.pojo.PageBean;
import com.xuchangan.pojo.Result;
import com.xuchangan.service.NutrientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nutrient")
public class NutrientController {

    @Autowired
    private NutrientService nutrientService;

    @GetMapping("list")
    public Result<List<Nutrient>> list(){
        List<Nutrient> result = nutrientService.list();
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

}
