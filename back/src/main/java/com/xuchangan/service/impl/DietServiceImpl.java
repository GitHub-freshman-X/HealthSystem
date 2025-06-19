package com.xuchangan.service.impl;

import com.xuchangan.mapper.DietMapper;
import com.xuchangan.pojo.DietFoodList;
import com.xuchangan.pojo.DietFoodView;
import com.xuchangan.service.DietService;
import com.xuchangan.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DietServiceImpl implements DietService {

    @Autowired
    private DietMapper dietMapper;

    @Override
    public List<DietFoodList> getDietFoods() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer usrId = (Integer) map.get("id");
        List<DietFoodView> rawList = dietMapper.getDietFoods(usrId);

        Map<String, DietFoodList> groupedMap = new LinkedHashMap<>();

        for (DietFoodView view : rawList) {
            // 构造唯一 key：用户 + 日期 + 餐次
            String key = view.getRealName() + "|" + view.getDietDate() + "|" + view.getMealType();

            // 如果不存在则创建
            DietFoodList group = groupedMap.computeIfAbsent(key, k -> {
                DietFoodList newGroup = new DietFoodList();
                newGroup.setRealName(view.getRealName());
                newGroup.setDietDate(view.getDietDate());
                newGroup.setMealType(view.getMealType());
                newGroup.setFoodList(new ArrayList<>());
                newGroup.setQuantityList(new ArrayList<>());
                newGroup.setImagePathList(new ArrayList<>());
                return newGroup;
            });

            // 添加数据到当前 group
            group.getFoodList().add(view.getFoodName());
            group.getQuantityList().add(view.getQuantity());
            group.getImagePathList().add(view.getImagePath());
        }

        return new ArrayList<>(groupedMap.values());

    }
}
