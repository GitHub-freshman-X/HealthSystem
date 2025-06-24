package com.xuchangan.controller;

import com.xuchangan.pojo.Food;
import com.xuchangan.pojo.PageBean;
import com.xuchangan.pojo.Result;
import com.xuchangan.pojo.User;
import com.xuchangan.service.AdminService;
import com.xuchangan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    // 查看所有用户
    @PostMapping("/allUsers")
    public Result<PageBean<User>> getAllUsers(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ) {
        PageBean<User> users = adminService.getAllUsers(pageNum, pageSize);
        return Result.success(users);
    }

    // 修改
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    // 删除用户
    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestParam Integer userId){
        adminService.deleteUser(userId);
        return Result.success();
    }

    // 查看所有食物
    @PostMapping("/allFoods")
    public Result<PageBean<Food>> getAllFoods(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ){
        PageBean<Food> foods = adminService.getAllFoods(pageNum, pageSize);
        return Result.success(foods);
    }

    @PostMapping("/updateFood")
    public Result updateFood(@RequestBody Food food){
        adminService.updateFood(food);
        return Result.success();
    }

    @PostMapping("/deleteFood")
    public Result deleteFood(@RequestParam Integer foodId){
        adminService.deleteFood(foodId);
        return Result.success();
    }

    @PostMapping("uploadFood")
    public Result uploadFood(@RequestBody Food food){
        adminService.uploadFood(food);
        return Result.success();
    }

}
