package com.example.ingredieat.backend;

import java.util.Arrays;
import java.util.HashSet;

public class Test {


    public static void main(String[] args) {
        Library library = new Library();
        HashSet<Ingredient> meat = new HashSet<>(Arrays.asList(new Ingredient("Beef", true), new Ingredient("Beef steak", true), new Ingredient("Chicken", true)));


        HashSet<Ingredient> search = library.search("bee", meat);
        System.out.println(search.toString());

    }
}


