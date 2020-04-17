package com.example.ingredieat.dao;

import com.example.ingredieat.entity.Ingredient;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IngredientDao {

    @Select("SELECT * FROM `ingredient` ORDER BY `name` WHERE `flag` = 1")
    List<Ingredient> listAllIngredients();

}
