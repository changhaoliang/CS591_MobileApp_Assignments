package com.example.ingredieat.entity;

import android.graphics.drawable.Drawable;


public class Item {
    private String name;
    private Drawable picture;
    private boolean isSelected;
    private Category category;

    public Item(String name, Drawable picture, boolean isSelected, Category category) {
        this.name = name;
        this.picture = picture;
        this.isSelected = isSelected;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public boolean getSeclected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Drawable getPicture() {
        return picture;
    }

    public Category getCategory() {
        return category;
    }

}
