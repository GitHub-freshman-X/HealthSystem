package com.xuchangan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuchangan.mapper.HealthMapper;
import com.xuchangan.pojo.HealthAvg;
import com.xuchangan.pojo.HealthReport;
import com.xuchangan.pojo.PageBean;
import com.xuchangan.pojo.UserHealthView;
import com.xuchangan.service.HealthService;
import com.xuchangan.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    private HealthMapper healthMapper;

    @Override
    public UserHealthView info(Integer userId) {
        List<UserHealthView> result = healthMapper.info(userId);
        if(result == null || result.isEmpty()) {
            return null; // 如果没有健康信息，返回null
        }
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

    @Override
    public PageBean<HealthReport> getReports(Integer pageNum, Integer pageSize, LocalDate recordDate) {
        PageBean<HealthReport> pb = new PageBean<>();

        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<HealthReport> result = healthMapper.getReports(userId, recordDate);

        PageInfo<HealthReport> pageInfo = new PageInfo<>(result);
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());

        return pb;
    }

    @Override
    public void uploadReport(String realName, String imgUrl) {
        // 根据名字查找userId
        Integer userId = healthMapper.getUserIdByName(realName);

        // 构建 HealthReport 对象
        HealthReport healthReport = new HealthReport();
        healthReport.setUserId(userId);
        healthReport.setRecordTime(LocalDateTime.now());
        healthReport.setImgUrl(imgUrl);

        // 调用mapper
        healthMapper.uploadReport(healthReport);
    }
}
