package com.xuchangan.mapper;

import com.xuchangan.pojo.DietFoodView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DietMapper {
    @Select("select * from diet_food_view where user_id=#{userId}")
    List<DietFoodView> getDietFoods(Integer userId);
}
