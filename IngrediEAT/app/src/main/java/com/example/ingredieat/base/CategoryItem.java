package com.example.ingredieat.base;

import android.graphics.drawable.Drawable;


public class CategoryItem {
    private Category category;
    private Drawable picture;
    //    private boolean isSelected;

    public CategoryItem(Category category, Drawable picture) {
        this.category = category;
        this.picture = picture;
    }


//    public boolean getSeclected() {
//        return isSelected;
//    }
//
//    public void setSelected(boolean isSelected) {
//        this.isSelected = isSelected;
//    }

    public Category getCategory() {
        return category;
    }

    public Drawable getPicture() {
        return picture;
    }

}
