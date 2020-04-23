package com.example.ingredieat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ingredieat.dao.*;
import com.example.ingredieat.entity.*;
import com.example.ingredieat.service.AsyncService;
import com.example.ingredieat.service.HomeService;
import com.example.ingredieat.utils.ApiKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class HomeServiceImpl implements HomeService {

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

    @Autowired
    AsyncService asyncService;

    @Override
    public List<Ingredient> listAllIngredients() {
        return ingredientDao.listAllIngredients();
    }

    @Override
    public List<Recipe> listRecipesByIngredientsNames(String googleId, String selectedIngredients) {
        List<Recipe> allRecipes = new ArrayList<>();
        List<Recipe> newRecipes = new ArrayList<>();

        RestTemplate rt = new RestTemplate();
        if (!selectedIngredients.isEmpty()) {
            String url = String.format("https://api.spoonacular.com/recipes/findByIngredients?ingredients=%s&apiKey=%s", selectedIngredients, ApiKeyUtils.getApiKey());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<String>(null, httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            String data = response.getBody();
            if (data != null) {
                JSONArray recipesInfo = JSONArray.parseArray(data);
                for (int i = 0; i < recipesInfo.size(); i++) {
                    JSONObject recipeInfo = recipesInfo.getJSONObject(i);
                    int id = recipeInfo.getInteger("id");
                    // Check whether the data of the recipe has existed in the database
                    Recipe recipe = findRecipeByGoogleIdAndRecipeId(googleId, id);
                    if (recipe == null) {
                        // Create a new recipe object;
                        recipe = new Recipe();
                        // Set up the basic information;
                        recipe.setId(id);
                        recipe.setImgUrl(recipeInfo.getString("image"));
                        recipe.setTitle(recipeInfo.getString("title"));
                        recipe.setLikes(0);
                        recipe.setRatings(0);
                        recipe.setStars(0);
                        recipeDao.insertNewRecipe(recipe);
                        // Set up the information related to the user
                        recipe.setLiked(false);
                        recipe.setRated(false);
                        recipe.setUserStars(0);
                        userRecipeDao.insertUserRecipe(googleId, recipe);
                        // Create a new recipeDetail object;
                        RecipeDetail recipeDetail = new RecipeDetail();
                        // Get the data of steps of the recipe
                        url = String.format("https://api.spoonacular.com/recipes/%d/analyzedInstructions?apiKey=%s", id, ApiKeyUtils.getApiKey());
                        response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
                        data = response.getBody();
                        List<Step> steps = new ArrayList<>();
                        if (data != null) {
                            JSONArray info = JSONArray.parseArray(data);
                            if (!info.isEmpty()) {
                                JSONArray stepsInfo = info.getJSONObject(0).getJSONArray("steps");
                                for (int j = 0; j < stepsInfo.size(); j++) {
                                    Step step = new Step();
                                    step.setRecipeId(id);
                                    //stepDao.insertStep(step);
                                    JSONObject stepInfo = stepsInfo.getJSONObject(j);
                                    // Get the equipments of the current step
                                    JSONArray equipmentsInfo = stepInfo.getJSONArray("equipment");
                                    HashSet<Integer> equipmentsIds = new HashSet<>();
                                    List<Equipment> equipments = new ArrayList<>();
                                    for (int t = 0; t < equipmentsInfo.size(); t++) {
                                        JSONObject equipmentInfo = equipmentsInfo.getJSONObject(t);
                                        int equipment_id = equipmentInfo.getInteger("id");
                                        if(!equipmentsIds.contains(equipment_id)) {
                                            Equipment equipment = new Equipment(equipmentInfo.getInteger("id"), equipmentInfo.getString("name"));
                                            //getOrInsertEquipment(equipment);
                                            //stepEquipmentDao.insertStepEquipment(step.getId(), equipment.getId());
                                            equipments.add(equipment);
                                            equipmentsIds.add(equipment_id);
                                        }
                                    }
                                    step.setEquipments(equipments);

                                    // Get the ingredients of the current step
                                    JSONArray ingredientsInfo = stepInfo.getJSONArray("ingredients");
                                    List<Ingredient> ingredients = new ArrayList<>();
                                    HashSet<Integer> ingredientsIds = new HashSet<>();
                                    for (int t = 0; t < ingredientsInfo.size(); t++) {
                                        JSONObject ingredientInfo = ingredientsInfo.getJSONObject(t);
                                        int ingredient_id = ingredientInfo.getInteger("id");
                                        if(!ingredientsIds.contains(ingredient_id)) {
                                            ingredientsIds.add(ingredient_id);
                                            String ingredient_name = ingredientInfo.getString("name");
                                            Ingredient ingredient = new Ingredient(ingredient_id, ingredient_name);
                                            //getOrInsertIngredient(ingredient);
                                            //stepIngredientDao.insertStepIngredient(step.getId(), ingredient.getId());
                                            ingredients.add(ingredient);
                                        }
                                    }
                                    step.setIngredients(ingredients);

                                    // Get the instruction of the current step
                                    String instruction = (String) stepInfo.getOrDefault("step", "");
                                    step.setInstruction(instruction);
                                    // stepDao.updateStepInstruction(step.getId(), instruction);
                                    steps.add(step);
                                }
                            }
                        }
                        recipeDetail.setSteps(steps);
                        recipe.setRecipeDetail(recipeDetail);
                        newRecipes.add(recipe);
                    }

                    // Get the dynamic usedIngredients data.
                    List<Ingredient> usedIngredients = new ArrayList<>();
                    JSONArray usedIngredientsInfo = recipeInfo.getJSONArray("usedIngredients");
                    for (int j = 0; j < usedIngredientsInfo.size(); j++) {
                        JSONObject usedIngredientInfo = usedIngredientsInfo.getJSONObject(j);
                        Ingredient ingredient = new Ingredient(usedIngredientInfo.getInteger("id"), usedIngredientInfo.getString("name"), usedIngredientInfo.getString("aisle"));
                        usedIngredients.add(ingredient);
                    }
                    recipe.getRecipeDetail().setUsedIngredients(usedIngredients);

                    // Get the dynamic missedIngredients data.
                    List<Ingredient> missedIngredients = new ArrayList<>();
                    JSONArray missedIngredientsInfo = recipeInfo.getJSONArray("missedIngredients");
                    for (int j = 0; j < missedIngredientsInfo.size(); j++) {
                        JSONObject missedIngredientInfo = missedIngredientsInfo.getJSONObject(j);
                        Ingredient ingredient = new Ingredient(missedIngredientInfo.getInteger("id"), missedIngredientInfo.getString("name"), missedIngredientInfo.getString("aisle"));
                        missedIngredients.add(ingredient);
                    }
                    recipe.getRecipeDetail().setMissedIngredients(missedIngredients);

                    allRecipes.add(recipe);
                }
            }
            if(!newRecipes.isEmpty()) {
                asyncService.saveRecipesData(googleId, newRecipes);
            }
        }
        return allRecipes;
    }

    /**
     * This method is used to find a Recipe object by the given google Id and recipe Id.
     */
    public Recipe findRecipeByGoogleIdAndRecipeId(String googleId, int recipeId) {
        Recipe recipe = recipeDao.getRecipeById(recipeId);
        if (recipe != null) {
            UserRecipe userRecipe = userRecipeDao.findUserRecipe(googleId, recipeId);
            if (userRecipe != null) {
                recipe.setLiked(userRecipe.isLiked());
                recipe.setRated(userRecipe.isRated());
                recipe.setUserStars(userRecipe.getUserStars());
            }else{
                recipe.setLiked(false);
                recipe.setRated(false);
                recipe.setUserStars(0);
                userRecipeDao.insertUserRecipe(googleId, recipe);
            }
            RecipeDetail recipeDetail = getRecipeDetail(recipeId);
            recipe.setRecipeDetail(recipeDetail);
        }
        return recipe;
    }

    public void getOrInsertEquipment(Equipment equipment) {
        if (equipmentDao.getEquipmentById(equipment.getId()) == null) {
            equipmentDao.insertEquipment(equipment);
        }
    }

    public void getOrInsertIngredient(Ingredient ingredient) {
        if (ingredientDao.getIngredientById(ingredient.getId()) == null) {
            ingredientDao.insertRecipeStepIngredient(ingredient);
        }
    }

    private RecipeDetail getRecipeDetail(int recipeId) {
        RecipeDetail recipeDetail = new RecipeDetail();
        List<Step> steps = stepDao.listStepsByRecipeId(recipeId);
        if (steps != null) {
            for (Step step : steps) {
                // Find the ingredients of the current step;
                List<Ingredient> ingredients = ingredientDao.listIngredientsByStepId(step.getId());
                step.setIngredients(ingredients);

                // Find the equipments of the current step;
                List<Equipment> equipments = equipmentDao.listEquipmentsByStepId(step.getId());
                step.setEquipments(equipments);
            }
        } else {
            steps = new ArrayList<>();
        }
        recipeDetail.setSteps(steps);

        return recipeDetail;
    }

    @Override
    public void updateUserRecipeLiked(UserRecipe userRecipe) {
        userRecipeDao.updateUserRecipeLiked(userRecipe);
        if(userRecipe.isLiked()) {
            recipeDao.increaseRecipeLikesBy1(userRecipe.getRecipeId());
        }else{
            recipeDao.decreaseRecipeLikesBy1(userRecipe.getRecipeId());
        }
    }

    @Override
    public float ratingRecipe(UserRecipe userRecipe) {
        // Get the original recipe data.
        Recipe recipe = recipeDao.getRecipeById(userRecipe.getRecipeId());
        int ratings = recipe.getRatings();
        float stars = recipe.getStars();

        // Get the user recipe data
        float userStars = userRecipe.getUserStars();
        stars = (ratings * stars + userStars) / (ratings + 1);
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String starsString = df.format(stars);
        stars = Float.valueOf(starsString);

        // Update the data in the database
        recipe.setRatings(ratings+1);
        recipe.setStars(stars);
        recipeDao.updateRecipe(recipe);
        userRecipeDao.updateUserRecipe(userRecipe);

        return stars;
    }

    @Override
    public List<Recipe> listFavoriteRecipesByGoogleId(String googleId) {
        List<Recipe> recipes = recipeDao.listFavoriteRecipesByGoogleId(googleId);
        for(Recipe recipe: recipes) {
            RecipeDetail recipeDetail = getRecipeDetail(recipe.getId());
            recipe.setRecipeDetail(recipeDetail);
        }

        return recipes;
    }
}
