package com.xuchangan.mapper;

import com.xuchangan.pojo.HealthAvg;
import com.xuchangan.pojo.UserHealthView;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HealthMapper {
    @Select("select * from user_health_view where user_id=#{userId}")
    List<UserHealthView> info(Integer userId);

    @Update("update user set height=#{height}")
    void updateUser(UserHealthView userHealthView);

    @Insert("insert into health_record(user_id, record_date, weight, body_water, protein, fat_mass, muscle_mass, bmr, visceral_fat) " +
            "values(#{userId}, #{recordDate}, #{weight}, #{bodyWater}, #{protein}, #{fatMass}, #{muscleMass}, #{bmr}, #{visceralFat})")
    void insertHealth(UserHealthView userHealthView);

    @Select("select * from health_avg where gender=#{gender} and age_min=#{minAge} and age_max=#{maxAge}")
    HealthAvg avg(String gender, Integer minAge, Integer maxAge);
}
