package com.example.ingredieat.controller;

;
import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.service.HomeService;
import com.example.ingredieat.utils.ApiKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    public List<Recipe> listRecipesByIngredientsNames(@RequestParam("selectedIngredients") String selectedIngredients,
                                                      @RequestParam("googleId") String googleId) {
        List<Recipe> allRecipes = new ArrayList<>();
        RestTemplate rt = new RestTemplate();
//        for(int i = 0; i < 10; i++) {
////            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
////                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 12000, 4.5f));
////            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
////                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 200, 3));
////            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
////            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
////                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
////            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
////            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
////                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
////            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
////        }
        if(!selectedIngredients.isEmpty()) {
            String url = String.format("https://api.spoonacular.com/recipes/findByIngredients?ingredients=%s&apiKey=%s", selectedIngredients, ApiKeyUtils.getApiKey());
            String data = rt.getForObject(url, String.class);
            System.out.println(data);
        }
        return allRecipes;
    }
}
