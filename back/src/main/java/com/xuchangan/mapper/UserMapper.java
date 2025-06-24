package com.xuchangan.mapper;

import com.xuchangan.pojo.FamilyRelation;
import com.xuchangan.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    @Select("select * from user where user_id=#{userId}")
    User findByUserId(Integer userId);

    @Insert("insert into user(username, password) values(#{username}, #{md5String})")
    void register(String username, String md5String);

    @Update("update user set real_name=#{realName}, gender=#{gender}, birth_date=#{birthDate}, height=#{height} where user_id=#{userId}")
    void update(User user);

    @Update("update user set avatar_url=#{avatarUrl} where user_id=#{id}")
    void uploadAvatar(Integer id, String avatarUrl);

    @Update("update user set password=#{md5String} where user_id=#{userId}")
    void updatePassword(Integer userId, String md5String);

    List<FamilyRelation> getFamilyMembers(Integer userId);

    @Insert("insert into family_relation(main_user_id, member_user_id, member_user_role) values(#{mainUserId}, #{memberUserId}, #{memberUserRole})")
    void addFamilyRelation(Integer mainUserId, int memberUserId, String memberUserRole);
}
