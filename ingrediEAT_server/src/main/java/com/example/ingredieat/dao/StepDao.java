package com.example.ingredieat.dao;


import com.example.ingredieat.entity.Step;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StepDao {

    @Select("SELECT * FROM `step` WHERE `recipe_id` = #{recipeId} ORDER BY `id`")
    List<Step> listStepsByRecipeId(int recipeId);

    //@Insert("INSERT * ste")


}
