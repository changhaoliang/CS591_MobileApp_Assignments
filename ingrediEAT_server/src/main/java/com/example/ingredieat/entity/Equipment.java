package com.example.ingredieat.entity;


import lombok.Data;

@Data
public class Equipment {
    private int id;
    private String name;

    public Equipment(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
