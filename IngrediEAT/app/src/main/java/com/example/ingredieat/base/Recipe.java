package com.example.ingredieat.base;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Recipe {
    private String imgUrl;
    private String title;
    private int likes, ratings;//likes - like counts; ratings - rating counts
    private boolean liked;//flag if liked
    private float stars;//stars - total average rating
    private RecipeDetail details;

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
    public void updataStars(float myStar){
        stars = (stars*ratings+myStar)/(ratings++);
    }

    public RecipeDetail getDetails(){
        if (details == null) {
            this.details = new RecipeDetail(this);
            details.getDetails();
        }
        return details;
    }
}
