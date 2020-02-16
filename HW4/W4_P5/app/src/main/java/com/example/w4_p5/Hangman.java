package com.example.w4_p5;

import java.util.HashMap;
import java.util.Random;

public class Hangman {
    // word, hint
    private HashMap<String, String> dictionary;
    private Round round;

    public Hangman() {
        dictionary = new HashMap<>();
        dictionary.put("apple", "food");
        dictionary.put("pear", "food");
        dictionary.put("burger", "food");

        dictionary.put("china", "country");
        dictionary.put("japan", "country");
        dictionary.put("america", "country");
        dictionary.put("russia", "country");

        dictionary.put("lion", "animal");
        dictionary.put("snake", "animal");
        dictionary.put("eagle", "animal");
        dictionary.put("fish", "animal");
        dictionary.put("wolf", "animal");

        dictionary.put("sunny", "weather");
        dictionary.put("rainy", "weather");
        dictionary.put("snowy", "weather");
    }

    public String[] chooseWord() {
        String[] res = new String[2];

        String[] keys = dictionary.keySet().toArray(new String[0]);

        Random random = new Random();
        String word = keys[random.nextInt(keys.length)];

        String hint = dictionary.get(word);

        res[0] = word;
        res[1] = hint;

        return res;
    }

    public void startGame() {
        String[] entry = chooseWord();
        round = new Round(entry[0], entry[1]);
    }
}
