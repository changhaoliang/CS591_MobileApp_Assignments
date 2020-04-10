package com.example.ingredieat.base;

import android.graphics.drawable.Drawable;

public class Recipe {
    private String imgUrl;
    private String title;
    private int likes;
    private boolean liked;
    private float stars;

    public Recipe(String imgUrl, String title){
        this.imgUrl = imgUrl;
        this.title = title;
        this.likes = 0;
        this.liked = false;
        this.stars = 0;
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
}
