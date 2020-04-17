package com.example.ingredieat.entity;


import lombok.Data;

import java.util.List;

@Data
public class RecipeDetail {
    private List<Ingredient> usedIngredients;
    private List<Ingredient> missedIngredients;
    private List<Step> steps;
}
