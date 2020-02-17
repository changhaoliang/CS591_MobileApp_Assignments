package com.example.w4_p5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private Button[][] letterButtons;
    private final int kbRows = 4, kbColumns = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        RelativeLayout myLayout = findViewById(R.id.root);
        WordInput wordInput = new WordInput(this, 5);
        RelativeLayout.LayoutParams wordInputLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wordInputLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        wordInputLayoutParams.addRule(RelativeLayout.BELOW, R.id.hangman);
        myLayout.addView(wordInput, wordInputLayoutParams);
        GridLayout keyBoard = buildKeyboard();
        RelativeLayout.LayoutParams keyBoardLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        keyBoardLayoutParams.topMargin = 300;
        keyBoardLayoutParams.addRule(RelativeLayout.BELOW, R.id.hangman);
        myLayout.addView(keyBoard, keyBoardLayoutParams);
    }

    public GridLayout buildKeyboard(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x/kbColumns;
        GridLayout keyBoard = new GridLayout(this);
        keyBoard.setColumnCount(kbColumns);
        keyBoard.setRowCount(kbRows);

        letterButtons = new Button[kbRows][kbColumns];
        ButtonHandler bh = new ButtonHandler();
        for (int row=0; row<kbRows; row++){
            for (int col=0; col<kbColumns; col++){
                letterButtons[row][col] = new Button(this);
                int letter = row==3?(64+row*7+col):(65+row*7+col);
                letterButtons[row][col].setText((char)letter+"");
                letterButtons[row][col].setOnClickListener(bh);
                keyBoard.addView(letterButtons[row][col], w, w);
            }
        }
        letterButtons[3][0].setVisibility(View.INVISIBLE);
        letterButtons[3][kbColumns-1].setVisibility(View.INVISIBLE);
        return keyBoard;
    }
    private boolean checkLetter(Button b){
        char c = b.getText().charAt(0);
        //if c is the answer return true, and set button invisible
        return false;
    }
    private class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v){
            for (int row=0; row<kbRows; row++){
                for (int col=0; col<kbColumns; col++){
                    if (v == letterButtons[row][col]){
                        checkLetter(letterButtons[row][col]);
                    }
                }
            }
        }
    }
}
