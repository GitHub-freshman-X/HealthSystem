package com.xuchangan.controller;

import com.xuchangan.pojo.ExerciseRecord;
import com.xuchangan.pojo.PageBean;
import com.xuchangan.pojo.Result;
import com.xuchangan.pojo.Statistics;
import com.xuchangan.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    // 获取所有运动记录
    @PostMapping("/allRecords")
    public Result<PageBean<ExerciseRecord>> getAllRecords(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String exerciseType,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){
        PageBean<ExerciseRecord> result = exerciseService.getAllRecords(pageNum, pageSize, name, exerciseType, startDate, endDate);
        return Result.success(result);
    }

    // 获取当前用户指定某天的所有运动记录
    @PostMapping("/allRecordsByDate")
    public Result<List<ExerciseRecord>> getAllRecordsByDate(LocalDate date){
        List<ExerciseRecord> records = exerciseService.getAllRecordsByDate(date);
        return Result.success(records);
    }

    // 上传新的运动记录
    @PostMapping("/upload")
    public Result upload(@RequestBody ExerciseRecord record){
        exerciseService.upload(record);
        return Result.success();
    }

    @PostMapping("/getSumTime")
    public Result<Statistics> getSumTime(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){
        Statistics result = exerciseService.getSumTime(startDate, endDate);
        return Result.success(result);
    }

}
