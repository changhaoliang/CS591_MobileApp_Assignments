package com.example.sse.interfragmentcommratingbar;

import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;

public class Picture {
    private Drawable image;
    private float rating;

    public Picture(Drawable image, float rating) {
        this.image = image;
        this.rating = rating;
    }

    public void setRating(float newRating) {
        this.rating = newRating;
    }

    public float getRating() {
        return rating;
    }

    public Drawable getImage() {
        return image;
    }

    public String toString() {
        return image.toString();
    }
}
