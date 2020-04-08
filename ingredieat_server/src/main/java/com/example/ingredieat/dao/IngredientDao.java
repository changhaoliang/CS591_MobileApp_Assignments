package com.example.ingredieat.dao;

import com.example.ingredieat.entity.Ingredient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IngredientDao {

    @Select("SELECT * FROM ingredient")
    List<Ingredient> listAllIngredients();
}
