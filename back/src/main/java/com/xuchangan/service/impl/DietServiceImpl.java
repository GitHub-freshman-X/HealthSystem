package com.xuchangan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuchangan.mapper.DietMapper;
import com.xuchangan.mapper.NutrientMapper;
import com.xuchangan.pojo.*;
import com.xuchangan.service.DietService;
import com.xuchangan.service.NutrientService;
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
    @Autowired
    private NutrientMapper nutrientMapper;

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
    public List<DietFoodList> getDietCalories(LocalDate dietDate) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Diet> mealKeys = dietMapper.getDistinctMeals(userId, dietDate, "");

        List<DietFoodList> resultList = new ArrayList<>();
        for (Diet key : mealKeys) {
            List<DietFoodView> views = dietMapper.getDietFoods(userId, key.getDietDate(), key.getMealType());

            DietFoodList group = new DietFoodList();
            group.setRealName(views.get(0).getRealName());
            group.setDietDate(key.getDietDate());
            group.setMealType(key.getMealType());

            Double totalCalories = 0.0;
            group.setFoodList(new ArrayList<>());
            group.setQuantityList(new ArrayList<>());
            group.setCaloriesList(new ArrayList<>());

            for (DietFoodView view : views) {
                // 获取当前食物所含“热量”
                FoodNutrient fn =  nutrientMapper.getFoodNutrient(view.getFoodName(), "热量").get(0);
                Double calories = view.getQuantity() / 100.0 * fn.getAmount();
                totalCalories += calories;
                group.getFoodList().add(view.getFoodName());
                group.getQuantityList().add(view.getQuantity());
                group.getCaloriesList().add(calories);
            }
            group.setTotalCalories(totalCalories);

            resultList.add(group);
        }

        return resultList;
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

    @Override
    public List<NutrientSufficient> getNutrientSufficient(String gender, Integer age, LocalDate dietDate, String pregnancy, String lactation) {
        List<NutrientSufficient> standardList = dietMapper.getNutrientSufficient(gender, age, dietDate, pregnancy, lactation);
        return standardList;
    }

    @Override
    public List<NutrientRec> getNutritiousMeal(String gender, Integer age){
        List<NutrientSufficient> avg = getAvgDeficiency(gender, age);
        // 现在我知道了每种营养素的摄入欠缺多少，接下来计算用什么食物补充

        List<NutrientRec> nutrientRecs = new ArrayList<>();
        for(NutrientSufficient ns : avg){
            if(ns.getIntakeAmount() >= ns.getRecommendAmount()) {
                continue; // 如果摄入量已经足够，则跳过
            }

            String nutrientName = ns.getNutrientName();

            // 获取最富含这种营养素的食物
            FoodNutrient fn = nutrientMapper.getFoodByNutrient(nutrientName).get(0);

            NutrientRec nr = new NutrientRec();
            nr.setNutrientName(nutrientName);
            nr.setFoodName(fn.getFoodName());
            nr.setRecAmount(ns.getRecommendAmount() / fn.getAmount() / 3 * 100.0); // 直接使用推荐值

            nutrientRecs.add(nr);
        }

        // 筛选重复食物
        Map<String, NutrientRec> uniqueRecs = new LinkedHashMap<>();
        for(NutrientRec nr : nutrientRecs){
            if(!uniqueRecs.containsKey(nr.getFoodName())) {
                uniqueRecs.put(nr.getFoodName(), nr);
            }else{
                // 如果已经存在，则比较推荐量，保留推荐量更大的
                NutrientRec existingRec = uniqueRecs.get(nr.getFoodName());
                if(nr.getRecAmount() > existingRec.getRecAmount()) {
                    uniqueRecs.put(nr.getFoodName(), nr);
                }
            }
        }

        return uniqueRecs.values().stream().toList();
    }

    List<NutrientSufficient> getAvgDeficiency(String gender, Integer age){
        // 我要计算从今天往前一星期的营养素缺失
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(7);
        List<NutrientSufficient> avg = new ArrayList<>();

        List<Nutrient> nutrientList = nutrientMapper.list(null);
        for(Nutrient nutrient: nutrientList){
            NutrientSufficient ns = new NutrientSufficient();
            ns.setNutrientName(nutrient.getName());
            ns.setIntakeAmount(0.0);
            ns.setRecommendAmount(0.0);
            avg.add(ns);
        }

        int count=0;
        for(LocalDate day = startDate; !day.isAfter(today) || count==0; day = day.plusDays(1)) {
            List<NutrientSufficient> list = dietMapper.getNutrientSufficient(gender, age, day, "0", "0");
            if(list == null || list.isEmpty()) {
                continue; // 如果没有数据，则跳过
            }

            count++;
            for(NutrientSufficient ns : list){
                // 累加每个营养素的摄入量和推荐量
                for(NutrientSufficient avgNs : avg){
                    if(avgNs.getNutrientName().equals(ns.getNutrientName())){
                        avgNs.setIntakeAmount(avgNs.getIntakeAmount() + ns.getIntakeAmount());
                        avgNs.setRecommendAmount(avgNs.getRecommendAmount() + ns.getRecommendAmount());
                        break;
                    }
                }
            }
        }

        // 求平均值
        for(NutrientSufficient ns : avg){
            ns.setIntakeAmount(ns.getIntakeAmount() / count);
            ns.setRecommendAmount(ns.getRecommendAmount() / count);
        }

        return avg;
    }

}



