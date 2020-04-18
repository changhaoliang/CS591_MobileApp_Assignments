package com.example.ingredieat.dao;

import com.example.ingredieat.entity.Ingredient;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IngredientDao {

    @Select("SELECT * FROM `ingredient`  WHERE `flag` = 1 ORDER BY `name`")
    List<Ingredient> listAllIngredients();

    @Select("SELECT * FROM `ingredient` WHERE `id` = #{id}")
    Ingredient getIngredientById(int id);

    @Insert("INSERT INTO `ingredient`(`id`, `name`, `flag`) values(#{id}, #{name}, 0)")
    void insertRecipeStepIngredient(Ingredient ingredient);
}
