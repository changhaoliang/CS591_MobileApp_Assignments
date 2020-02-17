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
//        dictionary.put("pear", "food");
//        dictionary.put("burger", "food");
//
//        dictionary.put("china", "country");
//        dictionary.put("japan", "country");
//        dictionary.put("america", "country");
//        dictionary.put("russia", "country");
//
//        dictionary.put("lion", "animal");
//        dictionary.put("snake", "animal");
//        dictionary.put("eagle", "animal");
//        dictionary.put("fish", "animal");
//        dictionary.put("wolf", "animal");
//
//        dictionary.put("sunny", "weather");
//        dictionary.put("rainy", "weather");
//        dictionary.put("snowy", "weather");
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

    public Round getRound() {
        return round;
    }
    public void startGame() {
        String[] res = chooseWord();
        round = new Round(res[0], res[1]);
    }

//    public static void main(String[] args){
//        Hangman hangman = new Hangman();
//        hangman.startGame("apple", "food");
//
//        hangman.getRound().updateScore('a');
//        hangman.getRound().updateScore('p');
//        hangman.getRound().updateScore('l');
//        hangman.getRound().updateScore('e');
//
//        System.out.println(hangman.getRound().getScore());
//        System.out.println(hangman.getRound().isWordGuessed());
//    }
}
