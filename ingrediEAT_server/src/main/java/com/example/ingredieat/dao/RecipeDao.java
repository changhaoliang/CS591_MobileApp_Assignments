package com.example.ingredieat.dao;


import com.example.ingredieat.entity.Recipe;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RecipeDao {

    @Select("SELECT * FROM `recipe` WHERE `id` = #{id}")
    @Results(value = {
            @Result(column = "img_url", property = "imgUrl")
    })
    Recipe getRecipeById(int id);

    @Insert("INSERT INTO `recipe`(`id`, `img_url`, `title`, `likes`, `ratings`, `stars`) values(#{id}, #{imgUrl}, #{title}, #{likes}, #{ratings}, #{stars})")
    void insertNewRecipe(Recipe recipe);

}
