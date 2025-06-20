package com.xuchangan.mapper;

import com.xuchangan.pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DietMapper {
    List<DietFoodView> getDietFoods(Integer userId, LocalDate dietDate, String mealType);

    List<Diet> getDistinctMeals(Integer userId, LocalDate dietDate, String mealType);

    // 插入diet表：userId, dietDate, mealType
    @Insert("insert into diet(user_id, diet_date, meal_type) values(#{userId}, #{dietDate}, #{mealType})")
    void insertIntoDiet(Integer userId, LocalDate dietDate, String mealType);

    // 统计diet表有多少条数据
    @Select("select count(*) from diet")
    Integer getDietCount();

    // 根据食物名字获取食物id
    @Select("select food_id from food where name = #{foodName}")
    Integer getFoodIdByName(String foodName);

    // 插入到 diet_food_view 表
    @Insert("insert into diet_food(diet_id, food_id, quantity) values(#{dietId}, #{foodId}, #{quantity})")
    void insertIntoDietFood(Integer dietId, Integer foodId, Double quantity);

    List<DietExerciseDiary> getDietExerciseDiary(Integer userId, LocalDate diaryDate);

    @Insert("insert into diet_exercise_diary(user_id, diary_date, water_intake, content) "
    + "values(#{userId}, #{diaryDate}, #{waterIntake}, #{content})")
    void uploadDiary(DietExerciseDiary dietExerciseDiary);

    List<DietNutrientIntake> getDietNutrientIntake(Integer userId, LocalDate dietDate, String mealType);

    List<NutrientSufficient> getNutrientSufficient(String gender, Integer age, LocalDate dietDate, String pregnancy, String lactation);
}
