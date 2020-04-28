package com.example.ingredieat.dao;


import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.UserRecipe;
import org.apache.ibatis.annotations.*;

/**
 * This interface is used to operate the `user_recipe` table.
 */
@Mapper
public interface UserRecipeDao {


    @Select("SELECT * FROM `user_recipe` WHERE `google_id` = #{googleId} AND `recipe_id` = #{recipeId}")
    @Results(value = {
            @Result(column = "user_stars", property = "userStars")
    })
    UserRecipe findUserRecipe(@Param("googleId") String googleId, @Param("recipeId") int recipeId);

    @Insert("INSERT INTO `user_recipe`(`google_id`, `recipe_id`, `liked`, `rated`, `user_stars`) values(#{googleId}, #{recipe.id}, #{recipe.liked}, #{recipe.rated}, #{recipe.userStars})")
    void insertUserRecipe(String googleId, Recipe recipe);

    @Update("UPDATE `user_recipe` SET `liked` = #{liked} WHERE `google_id` = #{googleId} AND `recipe_id` = #{recipeId}")
    void updateUserRecipeLiked(UserRecipe userRecipe);

    @Update("UPDATE `user_recipe` SET `rated` = #{rated}, `user_stars` = #{userStars} WHERE `google_id` = #{googleId} AND `recipe_id` = #{recipeId}")
    void updateUserRecipe(UserRecipe userRecipe);
}
