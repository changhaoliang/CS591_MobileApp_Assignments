package com.example.ingredieat.entity;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetail {
    private List<Ingredient> usedIngredients;
    private List<Ingredient> missedIngredients;
    private List<Step> steps;

    public RecipeDetail(){
//        getDetails();
    }

    public void getDetails(){
        usedIngredients = new ArrayList<>();
        missedIngredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public void setUsedIngredients(List<Ingredient> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    public void setMissedIngredients(List<Ingredient> missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Ingredient> getUsedIngredients(){return usedIngredients;}
    public List<Ingredient> getMissedIngredients(){return missedIngredients;}
    public List<Step> getSteps(){return steps;}
}