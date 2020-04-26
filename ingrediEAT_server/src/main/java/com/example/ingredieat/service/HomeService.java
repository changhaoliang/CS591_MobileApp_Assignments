package com.example.ingredieat.service;

import com.example.ingredieat.entity.Equipment;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.UserRecipe;

import java.util.List;

public interface HomeService {

    List<Ingredient> listAllIngredients();

    List<Recipe> listRecipesByIngredientsNames(String googleId, String selectedIngredients);

    void getOrInsertEquipment(Equipment equipment);

    void getOrInsertIngredient(Ingredient ingredient);

    void updateUserRecipeLiked(UserRecipe userRecipe);

    float ratingRecipe(UserRecipe userRecipe);

    List<Recipe> listFavoriteRecipesByGoogleId(String googleId);
}
