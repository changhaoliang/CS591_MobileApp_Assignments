package com.example.ingredieat.dao;


import com.example.ingredieat.entity.User;
import org.apache.ibatis.annotations.*;


/**
 * This interface is used to operate the `user` table.
 */
@Mapper
public interface UserDao {

    @Select("SELECT * FROM `user` WHERE `google_id` = #{googleId}")
    @Results(value = {
            @Result(column = "google_id", property = "googleId"),
            @Result(column = "given_name", property = "givenName"),
            @Result(column = "family_name", property = "familyName")
    })
    User getUserByGoogleId(@Param("googleId") String googleId);

    @Insert("INSERT INTO `user`(`google_id`, `email`, `given_name`, `family_name`) values(#{googleId}, #{email}, #{givenName}, #{familyName})")
    void insertUser(User user);
}
