package com.example.ingredieat.dao;


import com.example.ingredieat.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    @Select("SELECT * FROM `user` WHERE `google_id` = #{googleId}")
    User findUserByGoogleId(@Param("googleId") String googleId);

    @Insert("INSERT INTO `user`(`google_id`, `email`, `given_name`, `family_name`) values(#{googleId}, #{email}, #{givenName}, #{familyName})")
    void insertUser(User user);
}
