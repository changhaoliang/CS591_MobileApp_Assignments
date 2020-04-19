package com.example.ingredieat.dao;


import com.example.ingredieat.entity.Equipment;
import com.example.ingredieat.entity.Step;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StepDao {

    @Select("SELECT * FROM `step` WHERE `recipe_id` = #{recipeId} ORDER BY `id`")
    @Results(value = {
            @Result(column = "recipe_id", property = "recipeId")
    })
    List<Step> listStepsByRecipeId(int recipeId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO `step`(`recipe_id`, `instruction`) values(#{recipeId}, #{instruction})")
    void insertStep(Step step);
}
