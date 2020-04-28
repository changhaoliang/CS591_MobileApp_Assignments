package com.example.ingredieat.dao;

import com.example.ingredieat.entity.Ingredient;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * This interface is used to operate the `ingredient` table.
 */
@Mapper
public interface IngredientDao {

    @Select("SELECT `id`, `name`, `category` FROM `ingredient`  WHERE `flag` = 1 ORDER BY `name`")
    List<Ingredient> listAllIngredients();

    @Select("SELECT `id`, `name`, `category` FROM `ingredient` WHERE `id` = #{id}")
    Ingredient getIngredientById(int id);

    @Select("SELECT `ingredient`.`id`, `ingredient`.`name` " +
            "FROM `ingredient`, `step_ingredient` " +
            "WHERE `ingredient`.`id` = `step_ingredient`.`ingredient_id`" +
            "AND `step_ingredient`.`step_id` = #{stepId}")
    List<Ingredient> listIngredientsByStepId(int stepId);

    @Insert("INSERT INTO `ingredient`(`id`, `name`, `flag`) values(#{id}, #{name}, 0)")
    void insertRecipeStepIngredient(Ingredient ingredient);
}
