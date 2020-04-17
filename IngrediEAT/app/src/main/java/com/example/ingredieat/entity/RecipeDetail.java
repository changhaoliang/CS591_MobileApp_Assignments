package com.example.ingredieat.entity;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RecipeDetail {
    private List<Ingredient> ingredients;
    private List<Ingredient> missedIngredients;
    private List<Step> steps;

    public RecipeDetail(){
        getDetails();
    }

    public void getDetails(){
//        ingredients = new ArrayList<>();
//        missedIngredients = new ArrayList<>();
//        equipments = new ArrayList<>();
//        steps = new ArrayList<>();
//
//        //test layout
//        ingredients.add("light brown sugar");
//        ingredients.add("granulated sugar");
//        ingredients.add("baking powder");
//        missedIngredients.add("baking soda");
//        missedIngredients.add("pecans");
//
//        equipments.add("oven");
//        equipments.add("whisk");
//        equipments.add("bowl");
//
//        steps.add("Preheat the oven to 200 degrees F.");
//        steps.add("Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl.");
//        steps.add("Whisk together the eggs, buttermilk, butter and vanilla extract and vanilla bean in a small bowl.");
//        steps.add("Add the egg mixture to the dry mixture and gently mix to combine. Do not overmix.");
//        steps.add("Let the batter sit at room temperature for at least 15 minutes and up to 30 minutes before using.");

        //getRequest()
        //for each step, put equipment in equipment set
    }

    public List<Ingredient> getIngredients(){return ingredients;}
    public List<Ingredient> getMissedIngredients(){return missedIngredients;}
    public List<Equipment> getEquipments(){return equipments;}
    public List<Step> getSteps(){return steps;}
}