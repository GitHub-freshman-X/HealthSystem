package com.xuchangan.service;

import com.xuchangan.pojo.User;

public interface UserService {
    // 通过用户名查找用户
    User findByUsername(String username);

    // 注册
    void register(String username, String password);
}
