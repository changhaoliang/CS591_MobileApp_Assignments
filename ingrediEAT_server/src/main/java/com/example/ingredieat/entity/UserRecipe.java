package com.example.ingredieat.entity;

import lombok.Data;

@Data
public class UserRecipe {
    private String googleId;
    private int recipeId;
    private boolean liked;
    private boolean rated;
    private float userStars;
}
