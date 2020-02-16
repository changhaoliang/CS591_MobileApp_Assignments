package com.example.w4_p5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        RelativeLayout myLayout = new RelativeLayout(this);
        setContentView(myLayout);
        WordInput wordInput = new WordInput(this, 5);
        myLayout.addView(wordInput);
    }
}
