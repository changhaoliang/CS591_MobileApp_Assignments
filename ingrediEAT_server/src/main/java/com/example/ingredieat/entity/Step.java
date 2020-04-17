package com.example.ingredieat.entity;

import lombok.Data;

import java.util.List;

@Data
public class Step {
    private int id;
    private List<Ingredient> ingredients;
    private List<Equipment> equipments;
    private String instruction;
}
