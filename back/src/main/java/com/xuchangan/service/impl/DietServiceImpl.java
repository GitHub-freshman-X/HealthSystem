package com.xuchangan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuchangan.mapper.DietMapper;
import com.xuchangan.pojo.Diet;
import com.xuchangan.pojo.DietFoodList;
import com.xuchangan.pojo.DietFoodView;
import com.xuchangan.pojo.PageBean;
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

        PageInfo<DietFoodList> pageInfo = new PageInfo<>(resultList);
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());

        return pb;

    }
}
