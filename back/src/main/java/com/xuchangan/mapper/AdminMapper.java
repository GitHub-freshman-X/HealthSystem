package com.xuchangan.mapper;

import com.xuchangan.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from user")
    List<User> getAllUsers();

    @Delete("delete from user where user_id = #{userId}")
    void deleteUser(Integer userId);
}
