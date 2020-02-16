package com.example.w4_p5;

public class Test {
    public static void main(String[] args){
        Hangman hangman = new Hangman();
        String[] res = hangman.chooseWord();

        System.out.println(res[0]);
    }
}