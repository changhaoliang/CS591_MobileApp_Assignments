package com.example.ingredieat.base;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetail {
    private Recipe recipe;
    private List<String> ingredients, missedIngredients,
            equipments, steps;

    public RecipeDetail(Recipe recipe){
        this.recipe = recipe;
        ingredients = new ArrayList<>();
        //missedIngredients = new ArrayList<>();
        equipments = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public void getDetails(){

    }

    public List<String> getIngredients(){return ingredients;}
    public List<String> getMissed(){return missedIngredients;}
    public List<String> getEquipments(){return equipments;}
    public List<String> getSteps(){return steps;}
}