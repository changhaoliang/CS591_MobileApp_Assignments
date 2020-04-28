package com.example.ingredieat.controller;

import com.example.ingredieat.bean.Params1;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.User;
import com.example.ingredieat.entity.UserRecipe;
import com.example.ingredieat.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is used to handle the http requests sent by the client side related with the home activity.
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    /**
     * This method is used to list all the ingredients in the database.
     * @return
     */
    @GetMapping("/ingredients")
    public List<Ingredient> listAllIngredients() {

        return homeService.listAllIngredients();
    }

    /**
     * This method is used to list the recommended recipes by the input ingredients names.
     * @param params1
     * @return
     */
    @PostMapping("/listRecipesByIngredientsNames")
    public List<Recipe> listRecipesByIngredientsNames(@RequestBody Params1 params1) {
        return homeService.listRecipesByIngredientsNames(params1.getGoogleId(), params1.getSelectedIngredients());
    }

    /**
     * This method is used to update the value of `liked` column of one record in the `user_recipe` table.
     * @param userRecipe
     */
    @PostMapping("/updateUserRecipeLiked")
    public void updateUserRecipeLiked(@RequestBody UserRecipe userRecipe) {

        homeService.updateUserRecipeLiked(userRecipe);
    }

    /**
     * This method is used to update the value of `user_stars` column of one record in the `user_recipe` table
     * and the value of `stars` column of the corresponding record in the `recipe` table.
     * @param userRecipe
     * @return
     */
    @PostMapping("/ratingRecipe")
    public float ratingRecipe(@RequestBody UserRecipe userRecipe) {

        return homeService.ratingRecipe(userRecipe);
    }

    /**
     * This method is used to list favourite recipes of one specific user by the googleId.
     * @param user
     * @return
     */
    @PostMapping("/listFavoriteRecipesByGoogleId")
    public List<Recipe> listFavoriteRecipesByGoogleId(@RequestBody User user) {

        return homeService.listFavoriteRecipesByGoogleId(user.getGoogleId());
    }
}
