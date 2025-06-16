package com.xuchangan.service.impl;

import com.xuchangan.mapper.UserMapper;
import com.xuchangan.pojo.User;
import com.xuchangan.service.UserService;
import com.xuchangan.utils.Md5Util;
import com.xuchangan.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // 密码加密
        String md5String = Md5Util.getMD5String(password);
        userMapper.register(username, md5String);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }
}
