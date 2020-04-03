package com.example.ingredieat.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Library {
    private HashSet<Ingredient> ingredients;

    public Library() {}

    public Library(Library library) {
        this.ingredients = library.ingredients;
    }

    public HashSet<Ingredient> search(String keyword, HashSet<Ingredient> category) {
        HashSet<Ingredient> result = new HashSet<>();
        for (Ingredient ingredient : category) {
            System.out.println(ingredient.getName());
            if (ingredient.getName().toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                result.add(ingredient);
            }
        }
        return result;
    }


}
