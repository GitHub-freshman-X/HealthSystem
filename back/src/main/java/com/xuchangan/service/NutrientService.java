package com.xuchangan.service;

import com.xuchangan.pojo.Nutrient;

import java.util.List;

public interface NutrientService {
    // 查询所有营养素数据
    List<Nutrient> list();

    // 添加营养素
    void add(Nutrient nutrient);

    // 根据ID删除营养素
    void delete(Integer nutrientId);

    // 更新
    void update(Nutrient nutrient);
}
