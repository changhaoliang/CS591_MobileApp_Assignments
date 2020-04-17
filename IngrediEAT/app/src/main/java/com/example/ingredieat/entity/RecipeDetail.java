package com.example.ingredieat.entity;

import android.widget.ArrayAdapter;

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
        ingredients = new ArrayList<>();
        missedIngredients = new ArrayList<>();
        steps = new ArrayList<>();

        //test layout
//        ingredients.add(new Ingredient(0, "light brown sugar",""));
//        ingredients.add(new Ingredient(0,"granulated sugar",""));
//        ingredients.add(new Ingredient(0,"baking powder",""));
//        missedIngredients.add(new Ingredient(0,"baking soda",""));
//        missedIngredients.add(new Ingredient(0, "pecans", ""));
//
//        Step testStep = new Step();
//        List<String> ingredients = new ArrayList<>();
//        ingredients.add("light brown sugar");
//        ingredients.add("granulated sugar");
//        List<String> equipments = new ArrayList<>();
//        equipments.add("oven");
//        equipments.add("bowl");
//        testStep.setIngredients(ingredients);
//        testStep.setEquipments(equipments);
//        testStep.setInstruction("Preheat the oven to 200 degrees F.");
//        steps.add(testStep);
        //getRequest()
    }

    public List<Ingredient> getIngredients(){return ingredients;}
    public List<Ingredient> getMissed(){return missedIngredients;}
    public List<Step> getSteps(){return steps;}
}