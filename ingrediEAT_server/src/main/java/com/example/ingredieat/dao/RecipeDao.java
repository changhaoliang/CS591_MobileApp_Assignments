package com.example.ingredieat.dao;


import com.example.ingredieat.entity.Recipe;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RecipeDao {

    @Select("SELECT * FROM `recipe` WHERE `id` = #{id}")
    Recipe getRecipeById(int id);

    @Insert("INSERT INTO `recipe`(`id`, `img_url`, `title`, `likes`, `ratings`, `stars`) values(#{id}, #{imgUrl}, #{title}, #{likes}, #{ratings}, #{stars})")
    void insertNewRecipe(Recipe recipe);

}
