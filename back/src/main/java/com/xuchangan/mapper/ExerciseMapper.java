package com.xuchangan.mapper;

import com.xuchangan.pojo.ExerciseRecord;
import com.xuchangan.pojo.Statistics;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ExerciseMapper {

    // 根据名字查id
    @Select("select user_id from user where real_name=#{name}")
    Integer getIdByName(String name);

    List<ExerciseRecord> getAllRecords(Integer userId, String exerciseType, LocalDate startDate, LocalDate endDate);

    @Insert("insert into exercise_record(user_id, exercise_date,exercise_type, duration, calorie_burned, steps) " +
            "values(#{userId}, #{exerciseDate}, #{exerciseType}, #{duration}, #{calorieBurned}, #{steps})")
    void upload(ExerciseRecord record);

    // 统计各个总时长
    Statistics getSumTime(Integer userId, LocalDate startDate, LocalDate endDate);
}
