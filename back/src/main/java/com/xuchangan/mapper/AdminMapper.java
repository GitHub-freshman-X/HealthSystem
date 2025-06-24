package com.xuchangan.mapper;

import com.xuchangan.pojo.Food;
import com.xuchangan.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from user")
    List<User> getAllUsers();

    @Delete("delete from user where user_id = #{userId}")
    void deleteUser(Integer userId);

    @Select("select * from food")
    List<Food> getAllFoods();

    @Update("update food set name=#{name}, image_path=#{imagePath} where food_id=#{foodId}")
    void updateFood(Food food);

    @Delete("delete from food where food_id = #{foodId}")
    void deleteFood(Integer foodId);

    @Insert("insert into food(name, image_path) values(#{name}, #{imagePath})")
    void uploadFood(Food food);
}
