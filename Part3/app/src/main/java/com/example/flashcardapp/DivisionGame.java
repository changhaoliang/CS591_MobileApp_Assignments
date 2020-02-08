package com.example.flashcardapp;

import java.util.ArrayList;

public class DivisionGame {
    private int maxRound; // 10
    private int score;

    public DivisionGame() {
        score = 0;
    }

    public ArrayList<DivisionProblem> playGame(int maxRound) {
        this.maxRound = maxRound;
        ArrayList<DivisionProblem> problem = new ArrayList<DivisionProblem>(maxRound);
        for (int i = 0; i < maxRound; i++) {
            DivisionProblem newProblem = new DivisionProblem();
            newProblem.generateProblem();
            problem.add(newProblem);
        }
        return problem;
    }

    public void updateScore(int input, DivisionProblem problem) {
        if (problem.ifWin(input)) {
            score ++;
        }
    }

    public void resetGame() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public int getMaxRound() {
        return maxRound;
    }
}
