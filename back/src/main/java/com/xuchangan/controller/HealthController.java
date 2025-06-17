package com.xuchangan.controller;

import com.xuchangan.pojo.HealthAvg;
import com.xuchangan.pojo.Result;
import com.xuchangan.pojo.UserHealthView;
import com.xuchangan.service.HealthService;
import com.xuchangan.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
