package com.xuchangan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuchangan.mapper.AdminMapper;
import com.xuchangan.pojo.Food;
import com.xuchangan.pojo.PageBean;
import com.xuchangan.pojo.User;
import com.xuchangan.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public PageBean<User> getAllUsers(Integer pageNum, Integer pageSize) {
        PageBean<User> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = adminMapper.getAllUsers();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());
        return pb;
    }

    @Override
    public void deleteUser(Integer userId) {
        adminMapper.deleteUser(userId);
    }

    @Override
    public PageBean<Food> getAllFoods(Integer pageNum, Integer pageSize) {
        PageBean<Food> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Food> foods = adminMapper.getAllFoods();
        PageInfo<Food> pageInfo = new PageInfo<>(foods);
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());
        return pb;
    }

    @Override
    public void updateFood(Food food) {
        adminMapper.updateFood(food);
    }

    @Override
    public void deleteFood(Integer foodId) {
        adminMapper.deleteFood(foodId);
    }

    @Override
    public void uploadFood(Food food) {
        adminMapper.uploadFood(food);
    }
}
