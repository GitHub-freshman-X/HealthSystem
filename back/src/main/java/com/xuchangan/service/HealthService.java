package com.xuchangan.service;

import com.xuchangan.pojo.UserHealthView;

public interface HealthService {
    // 获取用户健康信息
    UserHealthView info(Integer userId);

    // 更新用户健康信息
    void update(UserHealthView userHealthView);
}
