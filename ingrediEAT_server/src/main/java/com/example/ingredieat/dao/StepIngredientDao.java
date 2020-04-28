package com.example.ingredieat.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * This interface is used to operate the `step_ingredient` table.
 */
@Mapper
public interface StepIngredientDao {

    @Select("SELECT `ingredient_id` FROM `step_ingredient` WHERE `step_id` = #{stepId}")
    List<Integer> listIngredientsIdsByStepId(int stepId);

    @Insert("INSERT INTO `step_ingredient`(`step_id`, `ingredient_id`) values(#{stepId}, #{ingredientId})")
    void insertStepIngredient(int stepId, int ingredientId);
}
