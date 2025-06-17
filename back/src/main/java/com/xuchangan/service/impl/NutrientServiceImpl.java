package com.xuchangan.service.impl;

import com.xuchangan.mapper.NutrientMapper;
import com.xuchangan.pojo.Nutrient;
import com.xuchangan.service.NutrientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutrientServiceImpl implements NutrientService {

    @Autowired
    private NutrientMapper nutrientMapper;

    @Override
    public List<Nutrient> list() {
        return nutrientMapper.list();
    }

    @Override
    public void add(Nutrient nutrient) {
        nutrientMapper.add(nutrient);
    }

    @Override
    public void delete(Integer nutrientId) {
        nutrientMapper.delete(nutrientId);
    }

    @Override
    public void update(Nutrient nutrient) {
        nutrientMapper.update(nutrient);
    }
}
