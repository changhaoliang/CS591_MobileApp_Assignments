package com.example.ingredieat.dao;


import com.example.ingredieat.entity.UserRecipe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRecipeDao {

    @Select("SELECT * FROM `user_recipe` WHERE `google_id` = #{googleId} AND `recipeID` = #{recipeId}")
    UserRecipe findUserRecipe(@Param("googleId") String googleId, @Param("recipeId") int recipeId);
}
