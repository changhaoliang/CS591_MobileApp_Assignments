package com.example.ingredieat.entity;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int id;
    private String imgUrl;
    private String title;
    private int likes, ratings;//likes - like counts; ratings - rating counts
    private boolean liked;//flag if liked
    private boolean rated; //user has only one chance to rate a recipe
    private float stars, userStars;//stars - total average rating score, userStars - user's rating score
    private RecipeDetail recipeDetail;

    public Recipe() {

    }

    public Recipe(String imgUrl, String title){
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = 0;
        this.liked = false;
        this.rated = false;
        this.stars = 0;
        this.ratings = 0;
        this.userStars = 0;
    }

    public Recipe(String imgUrl, String title, int likes, float stars){
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = likes;
        this.liked = false;
        this.stars = stars;
    }

    public int getId() {
        return id;
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

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public void setUserStars(float userStars) {
        this.userStars = userStars;
    }

    public void setRecipeDetail(RecipeDetail recipeDetail) {
        this.recipeDetail = recipeDetail;
    }

    public String getTitle(){return title;}

    public String getLikes(){return likes+"";}

    public boolean getLiked(){return liked;}

    public boolean getRated(){return rated;}

    public float getStars(){return stars;}

    public float getUserStars(){return userStars;}

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
        userStars = myStar;
        rated = true;
        stars = (stars*ratings+myStar)/(++ratings);
    }

    public RecipeDetail getRecipeDetail(){
        if (recipeDetail == null) {
            this.recipeDetail = new RecipeDetail();
            List<Ingredient> usedIngredients = new ArrayList<>();
            List<Ingredient> missedIngredients = new ArrayList<>();
            List<Step> steps = new ArrayList<>();
            recipeDetail.setUsedIngredients(usedIngredients);
            recipeDetail.setMissedIngredients(missedIngredients);
            recipeDetail.setSteps(steps);
        }
        return recipeDetail;
    }
}
