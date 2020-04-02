package com.example.ingredieat.backend;

public class Ingredient {
    private String name;
    private boolean ifSelected;

    public Ingredient() {}

    public Ingredient(String name, boolean ifSelected) {
        name = name;
        ifSelected = ifSelected;
    }

    public Ingredient(Ingredient ingredient) {
        name = ingredient.name;
        ifSelected = ingredient.ifSelected;
    }

    public String getName() {
        return name;
    }

    public boolean getIfSelected() {
        return ifSelected;
    }


}
