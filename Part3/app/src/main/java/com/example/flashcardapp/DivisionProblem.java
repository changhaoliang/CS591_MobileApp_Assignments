package com.example.flashcardapp;

import java.util.Random;

public class DivisionProblem {
    private int divider;
    private int dividend;
    private int result;

    public DivisionProblem() {
        dividend = 0;
        divider = 0;
    }
    public void generateProblem() {
        Random random = new Random();
        int n1 = random.nextInt(100);
        int n2 = 0;
        while (n2 == 0) {
            n2 = random.nextInt(20);
        }
        dividend = n1;
        result = n2;
        divider = dividend * result;
    }

    public boolean ifWin(int input) {
        return input == result;
    }

    public int getDivider() {
        return divider;
    }
    public int getDividend() {
        return dividend;
    }
}
