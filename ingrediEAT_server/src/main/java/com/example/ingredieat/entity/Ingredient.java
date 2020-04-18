package com.example.ingredieat.entity;


import lombok.Data;

@Data
public class Ingredient {
    private int id;
    private String name;
    private String category;

    public Ingredient() {

    }

    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
