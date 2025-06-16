package com.xuchangan.mapper;

import com.xuchangan.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    @Insert("insert into user(username, password) values(#{username}, #{md5String})")
    void register(String username, String md5String);

    @Update("update user set real_name=#{realName}, gender=#{gender}, birth_date=#{birthDate}, height=#{height} where user_id=#{userId}")
    void update(User user);
}
