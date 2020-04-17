package com.example.ingredieat.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface StepIngredientDao {

    @Select("SELECT `ingredient_id` WHERE `step_id` = #{stepId}")
    List<Integer> listIngredientsIdsByStepId(int stepId);
}
