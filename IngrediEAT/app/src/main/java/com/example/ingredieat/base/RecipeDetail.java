package com.example.ingredieat.base;

import java.util.List;

public class RecipeDetail {
    private Recipe recipe;
    private List<Step> steps;
    private float rating;

    public RecipeDetail(Recipe recipe){
        this.recipe = recipe;
    }
    private void getDetails(){
        //getResponse()
    }

    public void rate(float myRating){
        recipe.updataStars(myRating);
    }
}
