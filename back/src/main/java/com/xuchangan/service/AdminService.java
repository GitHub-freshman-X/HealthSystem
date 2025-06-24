package com.xuchangan.service;

import com.xuchangan.pojo.Food;
import com.xuchangan.pojo.PageBean;
import com.xuchangan.pojo.User;

import java.util.List;

public interface AdminService {
    PageBean<User> getAllUsers(Integer pageNum, Integer pageSize);

    void deleteUser(Integer userId);

    PageBean<Food> getAllFoods(Integer pageNum, Integer pageSize);

    void updateFood(Food food);

    void deleteFood(Integer foodId);

    void uploadFood(Food food);
}
