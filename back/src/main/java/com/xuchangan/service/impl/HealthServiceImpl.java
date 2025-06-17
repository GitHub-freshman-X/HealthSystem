package com.xuchangan.service.impl;

import com.xuchangan.mapper.HealthMapper;
import com.xuchangan.pojo.HealthAvg;
import com.xuchangan.pojo.UserHealthView;
import com.xuchangan.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    private HealthMapper healthMapper;

    @Override
    public UserHealthView info(Integer userId) {
        List<UserHealthView> result = healthMapper.info(userId);
        return result.get(result.size()-1);
    }

    @Override
    public void update(UserHealthView userHealthView) {
        // 更新用户表
        healthMapper.updateUser(userHealthView);

        // 向健康表中插入数据
        userHealthView.setRecordDate(LocalDate.now());
        healthMapper.insertHealth(userHealthView);
    }

    @Override
    public HealthAvg avg(String gender, Integer age) {
        Integer minAge = age - age % 10;
        Integer maxAge = minAge + 9;
        HealthAvg healthAvg = healthMapper.avg(gender, minAge, maxAge);
        return healthAvg;
    }
}
