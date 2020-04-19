package com.example.ingredieat.service;

import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.entity.Recipe;

import java.util.List;

public interface HomeService {

    List<Ingredient> listAllIngredients();

    List<Recipe> listRecipesByIngredientsNames(String googleId, String selectedIngredients);

}
