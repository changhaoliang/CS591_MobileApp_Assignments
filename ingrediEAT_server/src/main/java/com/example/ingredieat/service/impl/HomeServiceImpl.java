package com.example.ingredieat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ingredieat.dao.IngredientDao;
import com.example.ingredieat.dao.RecipeDao;
import com.example.ingredieat.dao.UserRecipeDao;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.UserRecipe;
import com.example.ingredieat.service.HomeService;
import com.example.ingredieat.utils.ApiKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    IngredientDao ingredientDao;

    @Autowired
    RecipeDao recipeDao;

    @Autowired
    UserRecipeDao userRecipeDao;


    @Override
    public List<Ingredient> listAllIngredients() {
        return ingredientDao.listAllIngredients();
    }

    @Override
    public List<Recipe> listRecipesByIngredientsName(String googleId, String selectedIngredients) {
        List<Recipe> allRecipes = new ArrayList<>();
        RestTemplate rt = new RestTemplate();
        if(!selectedIngredients.isEmpty()) {
            String url = String.format("https://api.spoonacular.com/recipes/findByIngredients?ingredients=%s&apiKey=%s", selectedIngredients, ApiKeyUtils.getApiKey());
            String data = rt.getForObject(url, String.class);
            if(data != null) {
                JSONArray recipesInfo = JSONArray.parseArray(data);
                for(int i = 0; i < recipesInfo.size(); i++) {
                    JSONObject recipeInfo = recipesInfo.getJSONObject(i);
                    int id = recipeInfo.getInteger("id");

                }
            }
        }
        return allRecipes;
    }

    /**
     * This method is used to find a complete Recipe object by the given google Id and recipe Id.
     * @param id
     * @return
     */
    public Recipe findRecipeByGoogleIdAndRecipeId(String googleId, int recipeId) {
        Recipe recipe = recipeDao.getRecipeById(recipeId);
        if(recipe != null) {
            UserRecipe userRecipe = userRecipeDao.findUserRecipe(googleId, recipeId);
            recipe.setLiked(userRecipe.isLiked());
            recipe.setLiked(userRecipe.isRated());
            recipe.set
        }

        return recipe;
    }
}
