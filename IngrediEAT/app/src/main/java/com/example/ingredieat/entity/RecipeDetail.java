package com.example.ingredieat.entity;


import java.util.ArrayList;
import java.util.List;

public class RecipeDetail {
    private List<Ingredient> usedIngredients;
    private List<Ingredient> missedIngredients;
    private List<Step> steps;

    public RecipeDetail(){
        usedIngredients = new ArrayList<>();
        missedIngredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public void getDetails(){

    }

    public List<Ingredient> getUsedIngredients(){return usedIngredients;}
    public List<Ingredient> getMissedIngredients(){return missedIngredients;}
    public List<Step> getSteps(){return steps;}
}