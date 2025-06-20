package com.xuchangan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuchangan.mapper.DietMapper;
import com.xuchangan.pojo.*;
import com.xuchangan.service.DietService;
import com.xuchangan.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DietServiceImpl implements DietService {

    @Autowired
    private DietMapper dietMapper;

    @Override
    public PageBean<DietFoodList> getDietFoods(Integer pageNum, Integer pageSize, LocalDate dietDate, String mealType) {
        // 完善分页查询
        PageBean<DietFoodList> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        // 获取按照餐次来计算的，第pageNum页的前pageSize个
        List<Diet> mealKeys = dietMapper.getDistinctMeals(userId, dietDate, mealType);

        List<DietFoodList> resultList = new ArrayList<>();
        for (Diet key : mealKeys) {
            List<DietFoodView> views = dietMapper.getDietFoods(userId, key.getDietDate(), key.getMealType());

            DietFoodList group = new DietFoodList();
            group.setRealName(views.get(0).getRealName());
            group.setDietDate(key.getDietDate());
            group.setMealType(key.getMealType());

            group.setFoodList(new ArrayList<>());
            group.setQuantityList(new ArrayList<>());
            group.setImagePathList(new ArrayList<>());

            for (DietFoodView view : views) {
                group.getFoodList().add(view.getFoodName());
                group.getQuantityList().add(view.getQuantity());
                group.getImagePathList().add(view.getImagePath());
            }

            resultList.add(group);
        }

        PageInfo<Diet> piView = new PageInfo<>(mealKeys);
        pb.setTotal(piView.getTotal());
        PageInfo<DietFoodList> pageInfo = new PageInfo<>(resultList);
        pb.setItems(pageInfo.getList());

        return pb;

    }

    @Override
    public void uploadDietFoods(DietFoodList dietFoodList) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        // 插入到diet表中
        dietMapper.insertIntoDiet(userId, dietFoodList.getDietDate(), dietFoodList.getMealType());

        // 将dietFoodList转换为DietFoodView列表
        List<DietFoodView> views = new ArrayList<>();

        List<String> foodList = dietFoodList.getFoodList();
        List<Double> quantityList = dietFoodList.getQuantityList();

        for(int i=0; i<foodList.size(); ++i){
            DietFoodView view = new DietFoodView();
            view.setDietDate(dietFoodList.getDietDate());
            view.setMealType(dietFoodList.getMealType());

            view.setFoodName(foodList.get(i));
            view.setQuantity(quantityList.get(i));

            views.add(view);
        }

        Integer dietId = dietMapper.getDietCount(); // 获取最新插入的diet的ID
        for(DietFoodView view : views){
            Integer foodId = dietMapper.getFoodIdByName(view.getFoodName());
            dietMapper.insertIntoDietFood(dietId, foodId, view.getQuantity());
        }
    }

    @Override
    public PageBean<DietExerciseDiary> getDietExerciseDiary(Integer pageNum, Integer pageSize, LocalDate diaryDate) {
        PageBean<DietExerciseDiary> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        List<DietExerciseDiary> diaryList = dietMapper.getDietExerciseDiary(userId, diaryDate);

        PageInfo<DietExerciseDiary> pageInfo = new PageInfo<>(diaryList);

        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());

        return pb;
    }

    @Override
    public void uploadDiary(DietExerciseDiary dietExerciseDiary) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        dietExerciseDiary.setUserId(userId);
        dietMapper.uploadDiary(dietExerciseDiary);
    }

    @Override
    public PageBean<DietNutrientIntake> getDietNutrientIntake(
            Integer pageNum,
            Integer pageSize,
            LocalDate dietDate,
            String mealType) {
        PageBean<DietNutrientIntake> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        List<DietNutrientIntake> intakeList = dietMapper.getDietNutrientIntake(userId, dietDate, mealType);

        PageInfo<DietNutrientIntake> pageInfo = new PageInfo<>(intakeList);
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());
        return pb;
    }
}



