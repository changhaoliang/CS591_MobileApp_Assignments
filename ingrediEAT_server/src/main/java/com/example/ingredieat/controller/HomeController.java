package com.example.ingredieat.controller;

;
import com.example.ingredieat.dto.RecipeDto;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/ingredients")
    public List<Ingredient> listAllIngredients() {

        return homeService.listAllIngredients();
    }

    @GetMapping("/listRecipesByIngredientsNames")
    public List<RecipeDto> listRecipesByIngredientsNames() {
        List<RecipeDto> allRecipes = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            allRecipes.add(new RecipeDto("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 12000, 4.5f));
            allRecipes.add(new RecipeDto("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 200, 3));
            allRecipes.add(new RecipeDto("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
            allRecipes.add(new RecipeDto("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
            allRecipes.add(new RecipeDto("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
            allRecipes.add(new RecipeDto("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
            allRecipes.add(new RecipeDto("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
        }
        return allRecipes;
    }
}
