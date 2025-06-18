package com.xuchangan.controller;

import com.xuchangan.pojo.HealthAvg;
import com.xuchangan.pojo.HealthReport;
import com.xuchangan.pojo.Result;
import com.xuchangan.pojo.UserHealthView;
import com.xuchangan.service.HealthService;
import com.xuchangan.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    @GetMapping("/info")
    public Result<UserHealthView> info(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        UserHealthView userHealthView = healthService.info(userId);
        return Result.success(userHealthView);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UserHealthView userHealthView){
        healthService.update(userHealthView);
        return Result.success();
    }

    // @RequestParam注解对应x-www-form-urlencoded格式的请求
    @PostMapping("/avg")
    public Result<HealthAvg> avg(@RequestParam String gender, @RequestParam Integer age){
        HealthAvg healthAvg = healthService.avg(gender, age);
        return Result.success(healthAvg);
    }

    // 获取当前用户所有健康报告
    @GetMapping("/getReports")
    public Result<List<HealthReport>> getReports(){
        List<HealthReport> result = healthService.getReports();
        return Result.success(result);
    }

    // 上传报告
    @PostMapping("/uploadReport")
    public Result uploadReport(@RequestParam String realName, @RequestParam String imgUrl){
        healthService.uploadReport(realName, imgUrl);
        return Result.success();
    }

}
