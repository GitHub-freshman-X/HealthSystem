package com.xuchangan.service;

import com.xuchangan.pojo.User;
import org.hibernate.validator.constraints.URL;

public interface UserService {
    // 通过用户名查找用户
    User findByUsername(String username);

    // 注册
    void register(String username, String password);

    // 更新用户基本信息
    void update(User user);

    // 上传头像
    void uploadAvatar(@URL String avatarUrl);
}
