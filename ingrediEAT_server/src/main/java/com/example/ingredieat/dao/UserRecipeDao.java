package com.example.ingredieat.dao;


import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.UserRecipe;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRecipeDao {

    @Select("SELECT * FROM `user_recipe` WHERE `google_id` = #{googleId} AND `recipeID` = #{recipeId}")
    UserRecipe findUserRecipe(@Param("googleId") String googleId, @Param("recipeId") int recipeId);

    @Insert("INSERT `user_recipe`(`google_id`, `recipe_id`, `liked`, `rated`, `userStars`) values(#{googleId}, #{recipe.id}, #{recipe.liked}, #{recipe.rated}, #{recipe.userStars})")
    void insertNewUserRecipeRecord(String googleId, Recipe recipe);
}
