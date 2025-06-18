package com.xuchangan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuchangan.mapper.ExerciseMapper;
import com.xuchangan.pojo.ExerciseRecord;
import com.xuchangan.pojo.PageBean;
import com.xuchangan.pojo.Statistics;
import com.xuchangan.service.ExerciseService;
import com.xuchangan.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Override
    public PageBean<ExerciseRecord> getAllRecords(Integer pageNum, Integer pageSize, String name,
                                                  String exerciseType, LocalDate startDate, LocalDate endDate) {
        // 1. 创建 PageBean 对象
        PageBean<ExerciseRecord> pb = new PageBean<>();

        // 2. 开启分页查询（必须在查询前）
        PageHelper.startPage(pageNum, pageSize);

        // 3. 获取当前用户 ID（或从 name 转换）
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        if (name != null && !name.isEmpty()) {
            userId = exerciseMapper.getIdByName(name);
        }

        // 4. 查询数据
        List<ExerciseRecord> records = exerciseMapper.getAllRecords(userId, exerciseType, startDate, endDate);

        // 5. 使用 PageInfo 封装分页结果
        PageInfo<ExerciseRecord> pageInfo = new PageInfo<>(records);

        // 6. 设置返回值
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());

        return pb;
    }

    @Override
    public void upload(ExerciseRecord record) {
        // 获取当前用户 ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        // 设置用户 ID
        record.setUserId(userId);

        // 调用 Mapper 方法插入记录
        exerciseMapper.upload(record);
    }

    @Override
    public Statistics getSumTime(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        Statistics results = exerciseMapper.getSumTime(userId, startDate, endDate);
        return results;
    }
}
