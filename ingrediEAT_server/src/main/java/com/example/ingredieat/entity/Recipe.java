package com.example.ingredieat.entity;

import lombok.Data;

@Data
public class Recipe {
    private int id;
    private String imgUrl;
    private String title;
    private int likes, ratings;
    private boolean liked;
    private float stars;
    private RecipeDetail recipeDetail;

    public Recipe(String imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
    }

    public Recipe(String imgUrl, String title, int likes, float stars) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = likes;
        this.stars = stars;
    }
}
