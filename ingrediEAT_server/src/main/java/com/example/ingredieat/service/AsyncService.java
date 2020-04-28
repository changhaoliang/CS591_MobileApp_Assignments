package com.example.ingredieat.service;


import com.example.ingredieat.entity.Recipe;

import java.util.List;

public interface AsyncService {
    /**
     * Use another thread to save the data of the given recipes to the database.
     * @param googleId
     * @param recipes
     */
    void saveRecipesData(String googleId, List<Recipe> recipes);
}
