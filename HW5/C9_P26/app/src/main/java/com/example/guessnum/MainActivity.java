package com.example.guessnum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements inputFragment.InputFragmentListner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void getGuessNum(int index) {
        resultFragment checkingFragment = (resultFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_result);
        checkingFragment.checkGuessNum(index);
    }
}
