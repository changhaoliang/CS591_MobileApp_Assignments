package com.example.temperatureconverter;

public class TempConverter {

    public TempConverter() {
    }

    public Double fToC(Double input) {
        return (input - 32) * 5 / 9;
    }

    public Double cToF(Double input) {
        return input * 9 / 5 + 32;
    }
}
