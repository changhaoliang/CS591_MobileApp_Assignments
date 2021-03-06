package com.example.w5_p3;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board extends Activity {
    private char[][] board;
    private int row_num;
    private int col_num;
    private InputStream input;

    private Set<Character> vowels = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    private Set<Character> consonants = new HashSet<Character>(Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'));
    private Set<Character> doubledConsonants = new HashSet<Character>(Arrays.asList('s', 'z', 'y', 'p', 'x', 'q'));
    private Set<String> usedWords;

    public Board() {
        this.board = new char[4][4];
        this.usedWords = new HashSet<String>();
    }

    public Board(char[][] board, int row_num, int col_num) {
        this.board = board;
        this.row_num = row_num;
        this.col_num = col_num;
        this.usedWords = new HashSet<String>();
    }

    public boolean checkLength(String word) {
        return word.length() >= 4;
    }

    public boolean checkVowels(String word) {
        int vowelNum = 0;
        for (int i = 0; i < word.length(); i++) {
            if (vowels.contains(word.charAt(i))) {
                vowelNum++;
            }
        }
        return vowelNum >= 2;
    }

    public int updateScore(String word) {
        if (!searchWord(word)) {
            return -10;
        }
        int score = 0;
        boolean ifSpecial = false;
        for (int i = 0; i < word.length(); i++) {
            if (doubledConsonants.contains(word.charAt(i))) {
                ifSpecial = true;
            }
            if (vowels.contains(word.charAt(i))) {
                score += 5;
            } else {
                score += 1;
            }
        }
        if (ifSpecial) {
            score *= 2;
        }
        return score;
    }

    public void addScoredWord(String newWord) {
        usedWords.add(newWord);
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public boolean searchWord(String word) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().equals(word.toLowerCase())) {
                    usedWords.add(word);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public char[][] shuffle() {
        char[] vowel = new char[]{'a', 'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'u'};
        char[] consonant = new char[]{'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'p', 'q', 'r', 's', 's', 's', 't', 't', 'v', 'w', 'x', 'y', 'z'};

        int num_row = 4;
        int num_col = 4;
        int vowel_num = vowel.length;
        int consonant_num = consonant.length;
        char[][] suffle_letters = new char[num_row][num_col];
        //-1- generate vowel char index
        int num_vowel = 4;
        HashSet<Integer> vowel_index_set = new HashSet<>();
        Random random = new Random();

        while (vowel_index_set.size() < num_vowel) {
            vowel_index_set.add(random.nextInt(16));
        }

        for (int i = 0; i < num_row; i++) {
            for (int j = 0; j < num_col; j++) {
                int temp_index;
                if (vowel_index_set.contains(i * 4 + j)) {
                    temp_index = random.nextInt(vowel_num);
                    suffle_letters[i][j] = vowel[temp_index];
                } else {
                    temp_index = random.nextInt(consonant_num);
                    suffle_letters[i][j] = consonant[temp_index];

                }
            }
        }

        this.board = suffle_letters;
        return suffle_letters;
    }

    public boolean wordRepeated(String word) {
        return usedWords.contains(word);
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isAdjacent(int[] currentIndex, int[] nextIndex) {
        double distanceSquare = Math.pow((currentIndex[0] - nextIndex[0]), 2) + Math.pow((currentIndex[1] - nextIndex[1]), 2);

        System.out.println(distanceSquare);
        return distanceSquare <= 2;
    }

}
