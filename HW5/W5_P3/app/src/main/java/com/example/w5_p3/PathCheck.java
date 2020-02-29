package com.example.w5_p3;


public class PathCheck{
    public static boolean isAdjacent(int[] currentIndex, int[] nextIndex) {
        double distance_square = Math.pow(currentIndex[0], nextIndex[0]) + Math.pow(currentIndex[1], nextIndex[1]);
        return distance_square <= 2;
    }
}

