package com.example.w4_p5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Round {
	
	private String word;
	private String hint;
	private int score;
	private HashSet<Character> guessedWords;
	private HashSet<Character> vowels;

	private HashMap<Character,ArrayList<Integer>> charMap = new HashMap<>();
	
	public Round(String word, String hint) {
		this.word = word;
		this.hint = hint;
		this.score = 0;
		this.guessedWords = new HashSet<>();
		this.vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

		for(int i = 0; i < word.length(); i++) {
			Character ch = word.charAt(i);
			ArrayList<Integer> position = new ArrayList<>();;
			if(charMap.containsKey(ch)){
				position = this.charMap.get(ch);
				position.add(i);
			}
			else{
				position.add(i);
			}
			this.charMap.put(ch, position);
		}
	}

	public void updateScore(Character ch) {
		if (isCharGuessed(ch)) {
			guessedWords.add(ch);
			if (vowels.contains(ch)) {
				score += 5;
			} else {
				score += 2;
			}
		}
	}

	public boolean isWordGuessed() {
		for (int i = 0; i < word.length(); i++) {
			if (!guessedWords.contains(word.charAt(i))) {
				return false;
			}
 		}
		return true;
	}

	public boolean isCharGuessed(Character ch) {
		return charMap.containsKey(ch);
	}

	public int getScore() {
		return score;
	}
	
	public ArrayList<Integer> getPosition(Character ch){
		return charMap.get(ch);
	}
	
	public String getWord() {
		return this.word;
	}
	
	public String getHint() {
		return this.hint;
	}


}
