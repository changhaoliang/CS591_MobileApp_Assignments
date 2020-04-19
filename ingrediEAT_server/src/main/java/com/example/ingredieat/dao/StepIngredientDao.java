package com.example.ingredieat.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface StepIngredientDao {

    @Select("SELECT `ingredient_id` FROM `step_ingredient` WHERE `step_id` = #{stepId}")
    List<Integer> listIngredientsIdsByStepId(int stepId);

    @Insert("INSERT INTO `step_ingredient`(`step_id`, `ingredient_id`) values(#{stepId}, #{ingredientId})")
    void insertStepIngredient(int stepId, int ingredientId);
}
