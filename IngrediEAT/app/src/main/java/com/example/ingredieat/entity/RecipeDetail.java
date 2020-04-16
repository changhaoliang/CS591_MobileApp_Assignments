package com.example.ingredieat.entity;

import java.util.List;

public class RecipeDetail {
    private List<Step> steps;

    public RecipeDetail() {

    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
