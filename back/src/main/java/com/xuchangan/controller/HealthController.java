package com.xuchangan.controller;

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

}
