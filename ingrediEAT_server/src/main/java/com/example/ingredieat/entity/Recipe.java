package com.example.ingredieat.entity;

import lombok.Data;

@Data
public class Recipe {
    private int id;
    private String imgUrl;
    private String title;
    private int likes, ratings;
    private boolean liked, rated;
    private float stars, userStars;
    private RecipeDetail recipeDetail;
}
