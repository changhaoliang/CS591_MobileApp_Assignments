package com.example.ingredieat.service;


import com.example.ingredieat.entity.Recipe;

import java.util.List;

public interface AsyncService {

    void saveRecipesData(String googleId, List<Recipe> recipes);
}
