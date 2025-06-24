package com.xuchangan.service;

import com.xuchangan.pojo.PageBean;
import com.xuchangan.pojo.User;

import java.util.List;

public interface AdminService {
    PageBean<User> getAllUsers(Integer pageNum, Integer pageSize);

    void delete(Integer userId);
}
