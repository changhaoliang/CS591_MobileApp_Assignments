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


    public Recipe() {

    }

    public Recipe(String imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = 0;
        this.liked = false;
        this.rated = false;
        this.stars = 0;
        this.ratings = 0;
        this.userStars = 0;
    }

    public Recipe(String imgUrl, String title, int likes, float stars) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = likes;
        this.liked = false;
        this.stars = stars;
    }
}
