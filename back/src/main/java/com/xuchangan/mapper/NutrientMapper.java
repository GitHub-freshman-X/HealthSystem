package com.xuchangan.mapper;

import com.xuchangan.pojo.Nutrient;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NutrientMapper {
    @Select("select * from nutrient")
    List<Nutrient> list();

    @Insert("insert into nutrient(name, unit, function_desc, target_benefits) " +
            "values (#{name}, #{unit}, #{functionDesc}, #{targetBenefits})")
    void add(Nutrient nutrient);

    @Delete("delete from nutrient where nutrient_id=#{nutrientId}")
    void delete(Integer nutrientId);

    @Update("update nutrient set name=#{name}, unit=#{unit}, function_desc=#{functionDesc}, target_benefits=#{targetBenefits} " +
            "where nutrient_id=#{nutrientId}")
    void update(Nutrient nutrient);
}
