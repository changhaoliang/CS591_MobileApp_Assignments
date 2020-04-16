package com.example.ingredieat.dto;


import com.example.ingredieat.entity.Ingredient;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {
    private int id;
    private String imgUrl;
    private String title;
    private int likes, ratings;
    private boolean liked;
    private float stars;
    private List<Ingredient> missingIngredients;
    private RecipeDetailDto recipeDetail;

    public RecipeDto(String imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
    }

    public RecipeDto(String imgUrl, String title, int likes, float stars) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = likes;
        this.stars = stars;
    }
}
