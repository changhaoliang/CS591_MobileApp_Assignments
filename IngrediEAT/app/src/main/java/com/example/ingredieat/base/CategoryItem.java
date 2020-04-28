package com.example.ingredieat.base;

import android.graphics.drawable.Drawable;

/*
    For store Category information including its picture
 */
public class CategoryItem {
    private Category category;
    private Drawable picture;

    public CategoryItem(Category category, Drawable picture) {
        this.category = category;
        this.picture = picture;
    }

    public Category getCategory() {
        return category;
    }

    public Drawable getPicture() {
        return picture;
    }

}
