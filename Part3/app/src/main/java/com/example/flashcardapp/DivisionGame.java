package com.example.flashcardapp;

public class DivisionGame {
    private int maxRound; // 10
    private int score;

    public DivisionGame() {
        score = 0;
    }

    public DivisionProblem[] playGame(int maxRound) {
        DivisionProblem[] problem = new DivisionProblem[maxRound];
        for (int i = 0; i < maxRound; i++) {
            problem[i] = new DivisionProblem();
            problem[i].generateProblem();
        }
        return problem;
    }

    public void updateScore(String input, DivisionProblem problem) {
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
