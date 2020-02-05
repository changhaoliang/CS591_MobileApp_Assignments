package com.example.part2;

public class TempConverter {

    public TempConverter() {
    }
    public Double fToC(Double input){
        return (input - 32) * 5 / 9;
    }

    public Double cToF(Double input){
        return input * 5 / 9 + 32;

    }
}
