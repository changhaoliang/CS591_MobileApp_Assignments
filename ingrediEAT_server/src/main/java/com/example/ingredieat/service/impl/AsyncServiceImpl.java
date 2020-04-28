package com.example.ingredieat.service.impl;


import com.example.ingredieat.dao.*;
import com.example.ingredieat.entity.Equipment;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.Step;
import com.example.ingredieat.service.AsyncService;
import com.example.ingredieat.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Async
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    @Lazy
    HomeService homeService;

    @Autowired
    IngredientDao ingredientDao;

    @Autowired
    RecipeDao recipeDao;

    @Autowired
    UserRecipeDao userRecipeDao;

    @Autowired
    StepDao stepDao;

    @Autowired
    EquipmentDao equipmentDao;

    @Autowired
    StepIngredientDao stepIngredientDao;

    @Autowired
    StepEquipmentDao stepEquipmentDao;

    /**
     * Use another thread to save the data of the given recipes to the database.
     * @param googleId
     * @param recipes
     */
    @Override
    public void saveRecipesData(String googleId, List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            for (Step step : recipe.getRecipeDetail().getSteps()) {
                stepDao.insertStep(step);
                for (Ingredient ingredient : step.getIngredients()) {
                    homeService.getOrInsertIngredient(ingredient);
                    stepIngredientDao.insertStepIngredient(step.getId(), ingredient.getId());
                }

                for (Equipment equipment : step.getEquipments()) {
                    homeService.getOrInsertEquipment(equipment);
                    stepEquipmentDao.insertStepEquipment(step.getId(), equipment.getId());
                }
            }
        }
    }
}
