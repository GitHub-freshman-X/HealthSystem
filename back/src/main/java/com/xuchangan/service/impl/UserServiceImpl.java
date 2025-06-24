package com.xuchangan.service.impl;

import com.xuchangan.mapper.UserMapper;
import com.xuchangan.pojo.FamilyRelation;
import com.xuchangan.pojo.User;
import com.xuchangan.service.UserService;
import com.xuchangan.utils.Md5Util;
import com.xuchangan.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public void uploadAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.uploadAvatar(id, avatarUrl);
    }

    @Override
    public String updatePassword(String oldPassword, String newPassword) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        User user = userMapper.findByUserId(userId);
        String oldMd5Password = user.getPassword();
        String chkOldPassword = Md5Util.getMD5String(oldPassword);
        if(!oldMd5Password.equals(chkOldPassword)) {
            return "旧密码错误";
        }

        // 加密
        String md5String = Md5Util.getMD5String(newPassword);

        userMapper.updatePassword(userId, md5String);
        return "密码更新成功";
    }

    @Override
    public List<FamilyRelation> getFamilyMembers() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<FamilyRelation> familyRelations = userMapper.getFamilyMembers(userId);
        return familyRelations;
    }

    @Override
    public User findByUserId(Integer memberUserId) {
        return userMapper.findByUserId(memberUserId);
    }

    @Override
    public void addFamilyRelation(Integer mainUserId, int memberUserId, String memberUserRole) {
        userMapper.addFamilyRelation(mainUserId, memberUserId, memberUserRole);
    }
}
