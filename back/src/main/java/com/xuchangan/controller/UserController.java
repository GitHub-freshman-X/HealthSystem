package com.xuchangan.controller;

import com.xuchangan.pojo.Result;
import com.xuchangan.pojo.User;
import com.xuchangan.service.UserService;
import com.xuchangan.utils.JwtUtil;
import com.xuchangan.utils.Md5Util;
import com.xuchangan.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    // 自动注入service层
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 注册
    @PostMapping("/register")
    public Result register(@RequestBody @Validated(User.Add.class) User user) {
        // 通过用户名查找，因为用户名必须唯一
        User u = userService.findByUsername(user.getUsername());
        if(u != null){
            return Result.error("用户名已被占用");
        }

        userService.register(user.getUsername(), user.getPassword());
        return Result.success();
    }

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody @Validated(User.Add.class) User loginUser) {
        User user = userService.findByUsername(loginUser.getUsername());
        if(user == null){
            return Result.error("用户名不存在");
        }

        // 密码加密
        String md5Password = user.getPassword();
        String md5LoginPassword = Md5Util.getMD5String(loginUser.getPassword());
        if(!md5Password.equals(md5LoginPassword)){
            return Result.error("密码错误");
        }

        // 生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getUserId());
        claims.put("username", user.getUsername());
        String jwtToken = JwtUtil.genToken(claims);

        // 将token存入到redis中
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(jwtToken, jwtToken, 1, TimeUnit.HOURS);

        return Result.success(jwtToken);
    }

    // 获取个人信息
    @GetMapping("/info")
    public Result getUserInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    // 修改个人基本信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated(User.Update.class) User user){
        userService.update(user);
        return Result.success();
    }

    // 上传头像
    @PatchMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam @URL String avatarUrl){
        userService.uploadAvatar(avatarUrl);
        return Result.success();
    }

    // 修改密码
    @PatchMapping("/updatePassword")
    public Result<String> updatePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ){
        String str =  userService.updatePassword(oldPassword, newPassword);
        return Result.success(str);
    }


    // -------------------------- 测试基本函数，不是对外接口 --------------------------
    @PostMapping("/findByUsername")
    public Result findByUsername(@RequestBody @Validated User user) {
        // 通过用户名查找用户
        User u = userService.findByUsername(user.getUsername());
        if (u == null) {
            return Result.error("用户不存在");
        }
        return Result.success();
    }

}
