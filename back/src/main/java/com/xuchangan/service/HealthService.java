package com.xuchangan.service;

import com.xuchangan.pojo.HealthAvg;
import com.xuchangan.pojo.HealthReport;
import com.xuchangan.pojo.UserHealthView;

import java.util.List;

public interface HealthService {
    // 获取用户健康信息
    UserHealthView info(Integer userId);

    // 更新用户健康信息
    void update(UserHealthView userHealthView);

    // 获取这个年龄阶段的平均值
    HealthAvg avg(String gender, Integer age);

    List<HealthReport> getReports();

    // 上传报告
    void uploadReport(String realName, String imgUrl);
}
