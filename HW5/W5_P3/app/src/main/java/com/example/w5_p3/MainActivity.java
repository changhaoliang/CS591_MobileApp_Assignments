package com.example.w5_p3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements GameFragment.GameFragmentListener, BottomFragment.BottomFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void updateScore(int score) {
        BottomFragment bottomFragment = (BottomFragment) getFragmentManager().findFragmentById(R.id.fragmentBottom);
        bottomFragment.updateScore(score);
    }

    @Override
    public void newGame() {
        GameFragment gameFragment = (GameFragment) getFragmentManager().findFragmentById(R.id.fragmentGame);
        gameFragment.newGame();
    }
}
