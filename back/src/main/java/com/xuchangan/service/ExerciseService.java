package com.xuchangan.service;

import com.xuchangan.pojo.ExerciseRecord;
import com.xuchangan.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseService {
    // 获取所有运动记录
    PageBean<ExerciseRecord> getAllRecords(Integer pageNum, Integer pageSize, String name,
                                           String exerciseType, LocalDate startDate, LocalDate endDate);

    // 上传新的运动记录
    void upload(ExerciseRecord record);
}
