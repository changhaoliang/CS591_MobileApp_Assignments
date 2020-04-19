package com.example.ingredieat;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class IngredientEATApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
