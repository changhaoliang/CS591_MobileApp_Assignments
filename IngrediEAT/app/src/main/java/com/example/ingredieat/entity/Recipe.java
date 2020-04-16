package com.example.ingredieat.entity;


import java.util.List;

public class Recipe {
    private int id;
    private String imgUrl;
    private String title;
    private int likes, ratings;
    private boolean liked;
    private float stars;
    private List<Ingredient> missingIngredients;
    private RecipeDetail recipeDetail;

    public Recipe() {

    }

    public Recipe(String imgUrl, String title){
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = 0;
        this.liked = false;
        this.stars = 0;
        this.ratings = 0;
    }

    public Recipe(String imgUrl, String title, int likes, float stars){
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = likes;
        this.liked = false;
        this.stars = stars;
    }

    public Recipe(int id, String imgUrl, String title, int likes, int ratings, boolean liked, float stars, List<Ingredient> missingIngredients, RecipeDetail recipeDetail) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = likes;
        this.ratings = ratings;
        this.liked = liked;
        this.stars = stars;
        this.missingIngredients = missingIngredients;
        this.recipeDetail = recipeDetail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public void setMissingIngredients(List<Ingredient> missingIngredients) {
        this.missingIngredients = missingIngredients;
    }

    public void setRecipeDetail(RecipeDetail recipeDetail) {
        this.recipeDetail = recipeDetail;
    }

    public String getTitle(){return title;}
    public String getLikes(){return likes+"";}
    public boolean getLiked(){return liked;}
    public float getStars(){return stars;}
    public String getImg(){return imgUrl;}
    public void like(){
        likes++;
        liked = true;
    }
    public void unlike(){
        likes--;
        liked = false;
    }
    public void updateStars(float myStar){
        stars = (stars*ratings+myStar)/(ratings++);
    }
}
