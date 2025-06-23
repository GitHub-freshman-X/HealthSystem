package com.xuchangan.mapper;

import com.xuchangan.pojo.FoodNutrient;
import com.xuchangan.pojo.Nutrient;
import com.xuchangan.pojo.NutrientRecommendDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NutrientMapper {
    List<Nutrient> list(String nutrientName);

    @Insert("insert into nutrient(name, unit, function_desc, target_benefits) " +
            "values (#{name}, #{unit}, #{functionDesc}, #{targetBenefits})")
    void add(Nutrient nutrient);

    @Delete("delete from nutrient where nutrient_id=#{nutrientId}")
    void delete(Integer nutrientId);

    @Update("update nutrient set name=#{name}, unit=#{unit}, function_desc=#{functionDesc}, target_benefits=#{targetBenefits} " +
            "where nutrient_id=#{nutrientId}")
    void update(Nutrient nutrient);

    List<NutrientRecommendDTO> getSupplyRecommend(Integer age, String gender, Double height, Double weight);

    List<FoodNutrient> getFoodNutrient(String foodName, String nutrientName);

    @Select("select * from food_nutrient_view where nutrient_name=#{nutrientName} order by amount desc")
    List<FoodNutrient> getFoodByNutrient(String nutrientName);

    @Select("select nutrient_name as name from nutrient_function_view where function_keywords like CONCAT('%', #{functionKeyword}, '%')")
    List<String> getNutrientsByFunction(String functionKeyword);
}
