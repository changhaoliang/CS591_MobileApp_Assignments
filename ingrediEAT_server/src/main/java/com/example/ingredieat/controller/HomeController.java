package com.example.ingredieat.controller;

import com.example.ingredieat.bean.Params1;
import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/listRecipesByIngredientsNames")
    public List<Recipe> listRecipesByIngredientsNames(@RequestBody Params1 params1) {

        return homeService.listRecipesByIngredientsNames(params1.getGoogleId(), params1.getSelectedIngredients());

//        List<Recipe> allRecipes = new ArrayList<>();
//        for(int i = 0; i < 10; i++) {
//            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
//                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 12000, 4.5f));
//            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
//                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 200, 3));
//            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
//            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
//                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
//            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
//            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
//                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
//            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
//        }
        //return allRecipes;
    }
}
